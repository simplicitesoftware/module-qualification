package com.simplicite.objects.Qualification;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.util.exceptions.*;

/**
 * Business object QualExercise
 */
public class QualExercise extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public boolean isDeleteEnable(String[] row) {
		
		return !isUsed(getFieldValue("row_id", row));
	}
	
	@Override
	public List<String> preValidate() {
		List<String> msgs = new ArrayList<>();
		
		if(!isNew()){
			Grant g = getGrant();
		
			if("ENUM".equals(getFieldValue("qualExAnswerType")) && getField("qualExChoicesEnumeration").hasChanged()){
				
				if(isUsed(getRowId())){
					
					msgs.add(Message.formatError("ENUM Utilisé", "Vous ne pouvez pas modifier les choix possibles, cette question est déjà utilisée dans un test", "qualExChoicesEnumeration"));
					
				}
				else{
					//Update enum (delete and recreate all codes + trad of refenum)
					String refEnum = getFieldValue("qualExRefenum");
					String idChoices = getFieldValue("qualExId") + "_CHOICES_ENUM";
					
					String flRowId = g.simpleQuery("select row_id from m_list where lov_name = '"+idChoices+"'");
				
					ObjectDB code = g.getTmpObject("FieldListCode");
					code.resetFilters();
					code.setFieldFilter("lov_list_id.lov_name", idChoices);
					code.setFieldFilter("row_module_id", ModuleDB.getModuleId("Qualification_Enum"));
					List<String[]> rslts = code.search();
					AppLog.info(getClass(), "preValidate", rslts.toString(), getGrant());
					if(!Tool.isEmpty(rslts)){
						for(String[] row : rslts){
							code.setValues(row, false);
							AppLog.info(getClass(), "preValidate", code.getFieldValue("lov_code"), getGrant());
							code.delete();
							
						}
					}
					createListCodesAndVals(flRowId, idChoices, getFieldValue("qualExChoicesEnumeration").split("@@@"), "FRA");
					resetEnums(idChoices);
				}
			}
		}
		else{
			setFieldValue("qualExId", getFieldValue("qualExType")+"-"+getFieldValue("qualExDifficulty")+"-0000");
		}
		
		
		//msgs.add(Message.formatInfo("INFO_CODE", "Message", "fieldName"));
		//msgs.add(Message.formatWarning("WARNING_CODE", "Message", "fieldName"));
		//
		
		return msgs;
	}
	
	@Override
	public String preCreate() {
		setFieldValue("qualExId", getFieldDisplayValue("qualExType")+"-"+getFieldDisplayValue("qualExDifficulty")+"-"+getGrant().getNextIdForColumn(getDBName(), "row_id"));
		return null;
	}
	
	/**
	 * Unit tests method
	 */
	@Override
	public String unitTests() {
		try {
			ObjectDB fl = getGrant().getTmpObject("FieldList");
			fl.resetValues();
			fl.setFieldFilter("mdl_name", "Qualification_Enum");
			
			for(String[] row : fl.search()){
				fl.setValues(row, false);
				String newName = fl.getFieldValue("lov_name").replace(" ", "");
				fl.setFieldValue("lov_name", newName); 
				fl.save();
			}
			return "Unit tests for" + getName();
		} catch (Exception e) {
			AppLog.error(null, e, getGrant());
			return e.getMessage();
		}
	}
	
	
	@Override
	public String postCreate() {
		
		/*
		if type is enum add id to QUAL_REF_ENUM_CHOICES
		
		create list of values with values in choices field
		
		create linked list between code ref and choices list on Test object
		*/
		
		Grant g = Grant.getSystemAdmin();
		
		String refRowId = "";
		
		String qualifID = ModuleDB.getModuleId("Qualification_Enum");
		
		if("ENUM".equals(getFieldValue("qualExAnswerType"))){
			
			String idRef = getFieldValue("qualExId") + "_REF_ENUM";
			
			ObjectDB lcRef = g.getTmpObject("FieldListCode");
			
			String flRefRowId = g.simpleQuery("select row_id from m_list where lov_name = 'QUAL_REF_ENUM_CHOICES'");
			
			lcRef.setFieldValue("lov_list_id", flRefRowId);
			lcRef.setFieldValue("lov_code", idRef.replace(" ", ""));
			lcRef.setFieldValue("lov_order_by", "999");
			lcRef.setFieldValue("row_module_id", qualifID);
			
			lcRef.create();
			
			refRowId = lcRef.getRowId();
			
			
			String[] enumVals = getFieldValue("qualExChoicesEnumeration").split("@@@");
			String idChoices = getFieldValue("qualExId") + "_CHOICES_ENUM";
			
			ObjectDB fl = g.getTmpObject("FieldList");
			fl.resetValues();
			fl.setFieldValue("lov_name", idChoices.replace(" ", ""));
			fl.setFieldValue("row_module_id", qualifID);
			
			fl.create();
			
			createListCodesAndVals(fl.getRowId(), idChoices,  enumVals, "FRA");
			setFieldValue("qualExRefenum", idRef);
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
			fll.setFieldValue("fll_list_id", fl.getRowId());
			
			fll.setFieldValue("row_module_id", qualifID);
			
			fll.create();
			
			
			resetEnums(idChoices);
			
		}
		
		return null;
	}
	
	
	@Override
	public String preDelete() {
		
		//Delete FieldListCode where lov_code = val(qualExRefenum)
		//Delete FieldList where lov_name = getFieldValue("qualExId") + "_CHOICES_ENUM"
		//Delete Linked list
		try{
			
			String code = getFieldValue("qualExRefenum");
			String idChoices = getFieldValue("qualExId") + "_CHOICES_ENUM";
			
			Grant g = getGrant().getSystemAdmin();
			
			ObjectDB fl = g.getTmpObject("FieldList");
			fl.resetFilters();
			fl.setFieldFilter("lov_name", idChoices);
			fl.setFieldFilter("row_module_id", ModuleDB.getModuleId("Qualification_Enum"));
			
			List<String[]> flRslts = fl.search();
		
			deleteLists(fl, flRslts);
			
			ObjectDB flc = g.getTmpObject("FieldListCode");
			flc.resetFilters();
			flc.setFieldFilter("lov_code", code);
			flc.setFieldFilter("row_module_id", ModuleDB.getModuleId("Qualification_Enum"));
			
			List<String[]> flcRslts = flc.search();
			
			deleteLists(flc, flcRslts);
			
		}
		catch(DeleteException e){
			AppLog.error(getClass(), "preDelete", "Delete error", e, getGrant());
		}
		
		
		//return Message.formatInfo("INFO_CODE", "Message", "fieldName");
		//return Message.formatWarning("WARNING_CODE", "Message", "fieldName");
		//return Message.formatError("ERROR_CODE", "Message", "fieldName");
		return null;
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
			
			AppLog.info(getClass(), "createListCodesAndVals", "Creating list item : "+code, getGrant());
			lcChoice.create();
			
			ObjectDB flv = g.getTmpObject("FieldListValue");
			
			String flvEnuRowId = g.simpleQuery("select row_id from m_list_value where lov_code_id = '"+lcChoice.getRowId()+"' and lov_lang='" + lang + "'");
			
			if(flv.select(flvEnuRowId)){
				flv.setFieldValue("lov_value", enumVals[i]);
				flv.save();
			}
			
		}
		
	}
	
	private void resetEnums(String lov){
		SystemTool.resetCacheList(lov);
		SystemTool.resetCacheList("QUAL_REF_ENUM_CHOICES");
	}
	
	private BusinessObjectTool.ReturnMessage deleteLists(ObjectDB o, List<String[]> items) throws DeleteException{
		
		if(!Tool.isEmpty(items)){
			for(String[] row : items){
				o.setValues(row, false);
				BusinessObjectTool bot = new BusinessObjectTool(o);
				return bot.delete();
			}
		}
		
		return null;
		
	}
	
	private boolean isUsed(String rowId){
		
		Grant g = getGrant().getSystemAdmin();
		
		return !"".equals(g.simpleQuery("select row_id from qual_ex_usr where QUAL_EXUSR_EX_ID = '"+rowId+"'"));
		
	}

			
}		
	