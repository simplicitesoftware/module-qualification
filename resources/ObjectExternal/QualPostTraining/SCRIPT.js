var QualPostTraining = QualPostTraining || (function($) {
	var app, prd, data = { list: null, item: null };

	/**
	 * Render
	 * @param params Parameters
	 * @function
	 */
	function render(params, qualTemplate) {
		
		try {
			if (typeof Vue === 'undefined') throw 'Vue.js not available';

			if (!params.pub) $('#demovuejsfrontend').css('min-height', '1000px');

			/*app = app || (params.pub
					? new Simplicite.Ajax(params.root, 'api', 'website', 'simplicite')  // External
					: Simplicite.Application  // Internal
				);*/
			app = new Simplicite.Ajax(params.root, 'uipublic');
			
			let output = [];
			let exams = params.exams;
			let tmp;
			let unknown = false;
			console.log(params.exams);
			
			if("" == params.examId){
				unknown = true;
				completed = true;
		    	submitted = true;
				start =  new FlowForm.QuestionModel({
					id: 'start',
					title: "Oups... ",
					content: "Il semblerait y avoir un problème avec l'affichage de votre questionnaire. Il est possible que votre token soit inexistant ou ait expiré.",
					type: FlowForm.QuestionType.SectionBreak,
					required: false,
				})
				output.push(start);
			}
			else{
				
				if(params.generic){
					start = new FlowForm.QuestionModel({
			            id: 'start',
			            title: "Questionnaire post-formation",
			            content: "Bonjour "+String.fromCodePoint("0x1F44B")+", Vous avez suivi une formation sur la plateforme Simplicité et nous aimerions avoir votre avis sur celle-ci. Ces quelques questions on pour but d'améliorer notre processus de formation. Bien entendu, il n'y a pas de bonnes ou de mauvaises réponses "+String.fromCodePoint(0x1F642)+" D'avance merci pour votre temps !",
			            type: FlowForm.QuestionType.SectionBreak,
			            required: true,
	        		});
				}
				else{
					start = new FlowForm.QuestionModel({
			            id: 'start',
			            title: "Questionnaires post-formation",
			            content: "Bonjour "+String.fromCodePoint("0x1F44B")+" et bienvenue sur notre portail de questionnaires Simplicité. Veillez cliquer sur 'suivant' ou Appuyez sur la touche ENTRÉE pour commencer",
			            type: FlowForm.QuestionType.SectionBreak,
			            required: true,
	        		});
				}
        	
				output.push(start);
				
				for(let k = 0; k < exams.length; k++){
					let input = exams[k].questions;
					let examTitle = exams[k].examTitle;
					for(let i = 0; i < input.length; i++){
						if(input[i].type == "ENUM"){
							let choices = [];
							let iChoices = input[i].enum.split("@@@");
							for(let j = 0; j<iChoices.length; j++){
								tmpChoice = new FlowForm.ChoiceOption({
									label: iChoices[j], 
								}),
								choices.push(tmpChoice);
							}
							tmp = new FlowForm.QuestionModel({
								id: input[i].id,
								title: input[i].title,
								helpTextShow: false,
								type: FlowForm.QuestionType.MultipleChoice,
								required: true,
								multiple: false,
								options: choices
						    })
						}
						else if(input[i].type == "TXT"){
							tmp = new FlowForm.QuestionModel({
								id:input[i].id,
								title: input[i].title,
								type: FlowForm.QuestionType.LongText,
								required: true
						    })
						}
						else if(input[i].type == "QST_BREAK"){
							tmp = new FlowForm.QuestionModel({
					            title: 'Vous allez répondre au questionnaire "'+examTitle+'".',
					            content: exams[k].examDescription,
					            type: FlowForm.QuestionType.SectionBreak,
			        		});
						}
						
						output.push(tmp);
					}
				}
			}
			
			let languageParams = new FlowForm.LanguageModel({
				enterKey: 'Entrée',
			    shiftKey: 'Maj',
		        continue: 'Suivant',
		        pressEnter: 'Appuyez sur :enterKey',
		        otherPrompt: 'Autre',
		        submitText: 'Valider',
			    placeholder : 'Saisissez votre réponse ici...',
			    percentCompleted : ':percent% complété',
			    multipleChoiceHelpTextSingle : 'Choisissez une valeur',
			    longTextHelpText : ':shiftKey + :enterKey pour aller à la ligne.'
			})
			
			new Vue({
				el: '#app',
				template:qualTemplate,
				data: function() {
					return {
						submitted: true,
						completed: true,
						language: languageParams,
						questions: output,
						isValid: !unknown
					}
				},
				
				methods: {
				
			      onComplete(completed, questionList) {
			        // This method is called whenever the "completed" status is changed.
			        this.completed = completed
			        this.submitted = true
			        
			        if(!unknown){
			        	this.store(this.questions);
			        }
			      },
			
			      store(qsts){
			      	console.log(qsts)
			      	var usrExObj = app.getBusinessObject("QualUserExam");
			      	usrExObj.resetFilters();
					usrExObj.getForCreate(function () {
						usrExObj.item.qualUsrexamUsrId = params.userId;
						usrExObj.item.qualUsrexamExamId = params.examId;
						usrExObj.create(function(){
							//callback of creation -> answer items should have been created
							qsts.forEach(qst => {
								
								if (qst.type !== FlowForm.QuestionType.SectionBreak) {
									let answer = qst.answer;
									var usrAnswerObj = app.getBusinessObject("QualExUsr");
									usrAnswerObj.resetFilters();
									usrAnswerObj.search(function(){
										usrAnswerObj.getForUpdate(function(){
											usrAnswerObj.item.qualExusrAnswer = answer;
											usrAnswerObj.update();
										}, usrAnswerObj.list[0].row_id)
									}, 
									{
										qualExusrUsrexamId:usrExObj.getRowId(),
										qualExusrExamexId__qualExamexExId__qualExId:qst.id
									});
								}
								
							})
						}, usrExObj.item);
					});
			      }
			    },
			});

		} catch(e) {
			console.error('Render error: ' + e.message);
		}
	}

	return { render: render };	
})(jQuery);