var QualStartTestExt = (function($) {
	
	function render(vals) {
		var app = $ui.getAjax();
		var count;
		try {
			var template = $('#qual-candidatehome-template').html();
			if (!template) {
				if (count>100) {
					app.error("Pas de template mustache");
					return;
				}
				setTimeout(function() {
					count++;
					render();
				}, 500);
				return;
			}

			load();
		}
		catch(e) {
			alert(e.message);
			app.error(e);
		}
	
	function load(){
	
		console.log(vals);
		$('#qual-candidatehome').html(Mustache.render(template, JSON.parse(vals)));
		
		var g = app.getGrant();
		$("[data-action='qual-goto-list-exams']").click(function(e){
			$ui.displayList(null, "QualUserExam", 
			{
				fixedFilters:{
					"qualUsrexamUsrId":g.userid
				}
			});
		})
		
		$("[data-action='qual-goto-exam']").click(function(){
			
			var qst = g.getTmpObject("QualExUsr");
			qst.search(function(){
				if(qst.count === 0){
					alert("Cet examen a déjà été complété");
				}
				else{
					$ui.displayForm(null, "QualExUsr", qst.list[0].row_id, {nav:"add", showNav:false});
				}
			}, 
			{
				"qualExusrUsrexamId":this.id,
				"qualExusrUsrexamId__qualUsrexamUsrId":g.userid,
				"qualExusrSubmitted":false
			});
			
		});
		
	}
	
}


return { render: render };
})(jQuery);