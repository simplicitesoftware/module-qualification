package com.simplicite.objects.Qualification;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.util.exceptions.*;

import org.json.*;

/**
 * Business object QualExercise
 */
public class QualExercise extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean isDeleteEnable(String[] row) {
		//Can't delete an exercise if it is used in a user exam
		return !isUsed(getFieldValue("row_id", row));
	}
	
	
	
	@Override
	public List<String> preValidate() {
		
		List<String> msgs = new ArrayList<>();
		
		if(!isNew()){
			Grant g = getGrant();
			
			
			if("ENUM".equals(getFieldValue("qualExAnswerType")) && getField("qualExChoicesEnumeration").hasChanged()){
				
				//if a enum question is used in a question, it can't be updated to prevent an update of the answers of already completed exams
				if(isUsed(getRowId())){
					//Message displayed to the user
					msgs.add(Message.formatError("ENUM Utilisé", "Vous ne pouvez pas modifier les choix possibles, cette question est déjà utilisée dans un test", "qualExChoicesEnumeration"));
					
				}
				else{
					
					//Update enum (delete and recreate all codes + trad of refenum)
					String refEnum = getFieldValue("qualExRefenum");

					String idChoices = getFieldValue("qualExId") + "_CHOICES_ENUM";
					deleteCodes(idChoices);
					
					String flRowId = g.simpleQuery("select row_id from m_list where lov_name = '"+idChoices+"'");
					createListCodesAndVals(flRowId, idChoices, getFieldValue("qualExChoicesEnumeration").split("@@@"), "FRA");
					
				}
			}
		}
		else{
			//Initialize field value
			setFieldValue("qualExId", getFieldValue("qualExType")+"-"+getFieldValue("qualExDifficulty")+"-0000");
		}
		
		return msgs;
	}
	
	@Override
	public String preCreate() {
		//Set auto-incremented unique id
		setFieldValue("qualExId", getFieldValue("qualExType")+"-"+getFieldValue("qualExDifficulty")+"-"+getGrant().getNextIdForColumn(getDBName(), "row_id"));
		return null;
	}
	
	@Override
	public String postCreate() {
		
		/*
		if type is ENUM add id to QUAL_REF_ENUM_CHOICES
		
		create list of values with values in choices field
		
		create linked list between code ref and choices list on Test object
		*/
		
		Grant g = Grant.getSystemAdmin();
		
		String qualifID = ModuleDB.getModuleId("Qualification_Enum");
		
		if("ENUM".equals(getFieldValue("qualExAnswerType"))){
			
			try{
				
				//Create item in reference list and get row id
				String refRowId = createFieldListCodeInRef();
				
				//Create list of value
				String flRowId = createListOfValues();
				
				/*String flRowId = createFieldListObjItem();
				String idChoices = getFieldValue("qualExId") + "_CHOICES_ENUM";
				String[] enumVals = getFieldValue("qualExChoicesEnumeration").split("@@@");
				createListCodesAndVals(flRowId, idChoices,  enumVals, "FRA");*/
				
				setFieldValue("qualExRefenum", getFieldValue("qualExId") + "_REF_ENUM");
				save();
				
				/*
				Original list : 
					Object : QualExUsr
					Objectfield = qualExamexExId.qualExRefenum / set : fll_objfield_id
					Code in Ref list of values / set : fll_code_id
					
				Linked list :
					Object : QualExUsr
					ObjectField = qualExusrAnswerEnumeration / set : fll_linked_id
					Answers list just created / set : fll_list_id
				*/
				
				ObjectDB fll = g.getTmpObject("FieldListLink");
				
				
				String refOfRowIdSql = "select of.row_id from m_objfield of "+
					"inner join m_object o on of.OBF_OBJECT_ID = o.row_id "+
					"inner join m_field f on of.obf_field_id = f.row_id "+
					"where OBJ_NAME = 'QualExUsr' "+
					"and FLD_NAME = 'qualExRefenum'";
					
				String ansOfRowIdSql = "select of.row_id from m_objfield of "+
					"inner join m_object o on of.OBF_OBJECT_ID = o.row_id "+
					"inner join m_field f on of.obf_field_id = f.row_id "+
					"where OBJ_NAME = 'QualExUsr' "+
					"and FLD_NAME = 'qualExusrAnswerEnumeration'";
					
				fll.setFieldValue("fll_objfield_id", g.simpleQuery(refOfRowIdSql));
				fll.setFieldValue("fll_code_id", refRowId);
				
				fll.setFieldValue("fll_linked_id", g.simpleQuery(ansOfRowIdSql));
				fll.setFieldValue("fll_list_id", flRowId);
				
				fll.setFieldValue("row_module_id", qualifID);
				
				fll.create();
			}
			catch(GetException e){
				AppLog.error(e, getGrant());
			}
			catch(ValidateException e){
				AppLog.error(e, getGrant());
			}
			catch(CreateException e){
				AppLog.error(e, getGrant());
			}
			
		}
		
		return null;
	}
	
	private String createListOfValues() throws ValidateException, CreateException, GetException{
		
		String flRowId = createFieldListObjItem();
		
		String idChoices = getFieldValue("qualExId") + "_CHOICES_ENUM";
		String[] enumVals = getFieldValue("qualExChoicesEnumeration").split("@@@");
		
		createListCodesAndVals(flRowId, idChoices,  enumVals, "FRA");
		
		return flRowId;
		
	}
	
	private String createFieldListObjItem() throws ValidateException, CreateException, GetException{
		
		Grant g = getGrant();
		String idChoices = (getFieldValue("qualExId") + "_CHOICES_ENUM").replace(" ", "");
		AppLog.info(idChoices, getGrant());
		ObjectDB fl = g.getTmpObject("FieldList");
		BusinessObjectTool bot = fl.getTool();
		bot.getForCreate();
		fl.setFieldValue("lov_name", idChoices);
		fl.setFieldValue("row_module_id", ModuleDB.getModuleId("Qualification_Enum"));
		
		bot.validateAndCreate();
		return fl.getRowId();
		
	}
	
	private String createFieldListCodeInRef(){
		
		Grant g = getGrant();
		
		String idRef = getFieldValue("qualExId") + "_REF_ENUM";
			
		ObjectDB lcRef = g.getTmpObject("FieldListCode");
		
		String flRefRowId = g.simpleQuery("select row_id from m_list where lov_name = 'QUAL_REF_ENUM_CHOICES'");
		
		lcRef.setFieldValue("lov_list_id", flRefRowId);
		lcRef.setFieldValue("lov_code", idRef.replace(" ", ""));
		lcRef.setFieldValue("lov_order_by", "999");
		lcRef.setFieldValue("row_module_id", ModuleDB.getModuleId("Qualification_Enum"));
		
		lcRef.create();
		return lcRef.getRowId();
	}
	
	@Override
	public String preDelete() {
		
		try{
			
			Grant g = getGrant().getSystemAdmin();
			String code = getFieldValue("qualExRefenum");
			String idChoices = getFieldValue("qualExId") + "_CHOICES_ENUM";
			
			//Delete FieldList where lov_code = val(qualExRefenum)			
			deleteValuesOfObject("FieldList", new JSONObject().put("lov_name", idChoices).put("row_module_id", ModuleDB.getModuleId("Qualification_Enum")));
			
			//Delete FieldListCode where lov_name = getFieldValue("qualExId") + "_CHOICES_ENUM"
			deleteValuesOfObject("FieldListCode", new JSONObject().put("lov_code", idChoices).put("row_module_id", ModuleDB.getModuleId("Qualification_Enum")));
			
		}
		catch(DeleteException e){
			AppLog.error(getClass(), "preDelete", "Delete error", e, getGrant());
		}
		
		return null;
	}	
	
	
	private void deleteValuesOfObject(String objName, JSONObject filters) throws DeleteException{
		
		ObjectDB obj = getGrant().getTmpObject(objName);
		BusinessObjectTool bot = obj.getTool();
		try{
			List<String[]> rows = bot.search(filters);
			deleteList(obj, rows);
		}
		catch(SearchException e){
			AppLog.error(e, getGrant());
		}
		
	}
	
	private void createListCodesAndVals(String flRowId, String idChoices, String[] enumVals, String lang){
		
		Grant g = getGrant();
		for(int i = 0; i < enumVals.length; i++){
			String code = idChoices + "_" + String.valueOf(i);
			ObjectDB lcChoice = g.getTmpObject("FieldListCode");
			lcChoice.resetValues();
			lcChoice.setFieldValue("lov_list_id", flRowId);
			lcChoice.setFieldValue("lov_code", code.replace(" ", ""));
			lcChoice.setFieldValue("lov_order_by", "999");
			lcChoice.setFieldValue("row_module_id", ModuleDB.getModuleId("Qualification_Enum"));
			
			AppLog.info(getClass(), "createListCodesAndVals", "Creating list item : "+ code.replace(" ", ""), getGrant());
			lcChoice.create();
			
			ObjectDB flv = g.getTmpObject("FieldListValue");
			
			String flvEnuRowId = g.simpleQuery("select row_id from m_list_value where lov_code_id = '"+lcChoice.getRowId()+"' and lov_lang='" + lang + "'");
			
			if(flv.select(flvEnuRowId)){
				flv.setFieldValue("lov_value", enumVals[i]);
				flv.save();
			}
			
		}
		
		resetEnums(idChoices);
		
	}
	
	/**
	 * Forces a clearcache of objects
	 */ 
	private void resetEnums(String lov){
		SystemTool.resetCacheList(lov);
		SystemTool.resetCacheList("QUAL_REF_ENUM_CHOICES");
	}
	
	private BusinessObjectTool.ReturnMessage deleteList(ObjectDB o, List<String[]> items) throws DeleteException{

		if(!Tool.isEmpty(items)){
			for(String[] row : items){
				o.setValues(row, false);
				BusinessObjectTool bot = new BusinessObjectTool(o);
				return bot.delete();
			}
		}
		return null;
	}
	
	private void deleteCodes(String idChoices){
		
		ObjectDB code = getGrant().getTmpObject("FieldListCode");
		code.resetFilters();
		code.setFieldFilter("lov_list_id.lov_name", idChoices);
		code.setFieldFilter("row_module_id", ModuleDB.getModuleId("Qualification_Enum"));
		List<String[]> rslts = code.search();
		if(!Tool.isEmpty(rslts)){
			for(String[] row : rslts){
				code.setValues(row, false);
				code.delete();
			}
		}
		
	}
	
	private boolean isUsed(String rowId){
		
		Grant g = getGrant().getSystemAdmin();
		
		return !"".equals(g.simpleQuery("select row_id from qual_ex_usr where QUAL_EXUSR_EX_ID = '"+rowId+"'"));
		
	}

			
}		
	