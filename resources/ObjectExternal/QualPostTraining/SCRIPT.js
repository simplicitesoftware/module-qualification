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
			
			app = new Simplicite.Ajax(params.root, 'uipublic');
			
			let output = [];
			let exams = params.exams;
			let tmp;
			let unknown = false;
			let generic = params.generic;
			
			let exObjRowId = "";
			let usrExObjIds = [];
			
			if("" == exams){
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
				
				/*
				Deactivated to make start of questions more generic
				if(generic){
					start = new FlowForm.QuestionModel({
			            id: 'start',
			            title: "Bonjour "+String.fromCodePoint("0x1F44B"),
			            subtitle:"Vous avez suivi une formation sur la plateforme Simplicité et nous aimerions avoir votre avis sur celle-ci. Ces quelques questions on pour but d'améliorer notre processus de formation. Bien entendu, il n'y a pas de bonnes ou de mauvaises réponses "+String.fromCodePoint(0x1F642),
			            description:"D'avance merci pour votre temps !",
			            type: FlowForm.QuestionType.SectionBreak,
			            required: true,
			            examId: exams[0].examId
	        		});
				}
				else{
					start = new FlowForm.QuestionModel({
			            id: 'start',
			            tagline:"Bonjour "+String.fromCodePoint("0x1F44B"),
			            title: "Bienvenue sur le portail de questionnaires Simplicité.",
		            	subtitle: "Vous allez ici répondre à un certain nombre de questions nous permettant d'évaluer votre niveau de compréhension de la plateforme.",
		            	description:"Lorsque vous êtes prêts, cliquez sur 'suivant' ou Appuyez sur la touche ENTRÉE pour commencer.",
			            type: FlowForm.QuestionType.SectionBreak,
			            required: true,
	        		});
				}*/
				
        		//Creates a 'SectionBreak' element that greets the user befor starting an exam
        		start = new FlowForm.QuestionModel({
	            	id: 'start',
		            title:"Bonjour "+String.fromCodePoint("0x1F44B"),
	            	subtitle: "Vous avez été invité à répondre à un questionnaire.",
	            	description:"Lorsque vous êtes prêts, cliquez sur 'suivant' ou Appuyez sur la touche ENTRÉE pour commencer.",
		            type: FlowForm.QuestionType.SectionBreak,
		            required: true,
        		});
        		
        		//Add the SectionBreak to the pile of elements to display
				output.push(start);
				
				//For each exam
				for(let k = 0; k < exams.length; k++){
					//get questions of exam
					let input = exams[k].questions;
					let examTitle = exams[k].examTitle;
					//for each question, create relevant type of question type
					for(let i = 0; i < input.length; i++){
						if(input[i].type == "ENUM"){
							tmp = createEnumElement(input[i], exams[k]);
						}
						else if(input[i].type == "TXT"){
							tmp = createTxtElement(input[i], exams[k]);
						}
						else if(input[i].type == "QST_BREAK"){
							tmp = createBreakElement(exams[k]);
						}
						
						//add element to output (pile of questions)
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
						loading:false,
						scored: false,
						submitted: false,
						completed: false,
						language: languageParams,
						questions: output,
						isValid: !unknown,
						generic:params.generic,
						scores:[]
					}
				},
				
				methods: {
				
				 onAnswer(qA) {
				 	let id = qA.id;
				 	//When a sectionbreak is passed and the id associated to it contains "exam", it's that a user has started an exam.
				 	//The row is created in the backend
					if(qA.type == FlowForm.QuestionType.SectionBreak && qA.id.includes("exam")){
						var usrExObj = app.getBusinessObject("QualUserExam");
						usrExObj.resetFilters();
						usrExObj.getForCreate(function () {
							usrExObj.item.qualUsrexamUsrId = params.userId;
							usrExObj.item.qualUsrexamExamId = qA.examId;
							usrExObj.create(function(){
								//store the exams row id
								exObjRowId = usrExObj.getRowId();
								//add the rowid to the list of exam rowids
								usrExObjIds.push(exObjRowId);
							}, usrExObj.item);
							
						});
					}
					
					if(qA.type !== FlowForm.QuestionType.SectionBreak){
						//if the type is other than a sectionbreak, it's a question -> set value in back
						let submittedValue = qA.answer;
						var usrAnswerObj = app.getBusinessObject("QualExUsr");
						usrAnswerObj.resetFilters();
						usrAnswerObj.search(function(){
							usrAnswerObj.getForUpdate(function(){
								usrAnswerObj.item.qualExusrSubmitted = true;
								usrAnswerObj.item.qualExusrAnswer = submittedValue;
								usrAnswerObj.update();
							}, usrAnswerObj.list[0].row_id);
						}, 
						{
							qualExusrUsrexamId:exObjRowId,
							qualExusrExamexId__qualExamexExId__qualExId:qA.id
						});
						
					}
				},
					
			      onComplete(completed, questionList) {
			        // This method is called whenever the "completed" status is changed.
			        this.completed = completed
			        //if the type of user is 'generic', the quizz is automatically submitted. If not, it is done by a user action
			        if(this.generic){
			        	this.onQuizSubmit();
			        }
			      },
			      
			      onQuizSubmit() {
			        // Set `submitted` to true so the form knows not to allow back/forward
			        // navigation anymore.
			        this.$refs.flowform.submitted = true
			        this.submitted = true
			        
			        if(!unknown){
			        	this.validateExams();
			        	
			        	//iterate through each exam
			        	exams.forEach(exam => {
			        		examQuestions = [];
			        		//get all questions of exam
			        		this.questions.forEach(qst => {
			        			if(qst.examId == exam.examId && qst.type !== FlowForm.QuestionType.SectionBreak){
			        				examQuestions.push(qst);
			        			}
			        		});
			        		//Scores are only calculated if the exam has right/wrong answers
			        		if(exam.examTitle != "Retours Formation"){
			        			let examScore = this.calculateScore(exam, examQuestions);
								this.scores.push(examScore);
			        		}
			        	})
			        	this.scored = true;
			        }
			      },
			      
			      //Calculate for specific exam
			      calculateScore(exam, qsts){
			      	score = 0;
			      	total = qsts.length;
			      	qsts.forEach(qst =>{
			      		let answer = qst.answer;
			      		if (answer == exam.answers[qst.id]) {
			              score++
			            }
			      	});
			      	score = Math.round((score / total)*100);
			      	return {"examId": exam.examId, "examTitle": exam.examTitle, "score": score};
			      },
			
				//Set status "DONE" on every exam passed by the user
				validateExams(){
					this.loading = true;
		    		var obj = app.getBusinessObject("QualUserExam");
	    			obj.resetFilters();
			      	usrExObjIds.forEach(id => {
						obj.getForUpdate(function(){
							obj.item.qualUsrexamEtat = "DONE";
							obj.update();
						},id);
					});
					
					//display "wait" section
					setTimeout(() => {
						this.loading = false;
					}, 1000)
					
				},
				
			      store(exam, qsts){
			      	
			      	this.loading = true;
					
					//display "wait" section
					setTimeout(() => {
						this.loading = false;
					}, 1000)
					
			      },
			      
			      
			    },
			    
			});
			 
		} catch(e) {
			console.error('Render error: ' + e.message);
		}
		
		function createEnumElement(input, exam){
			//create choice options
			let choices = [];
			let iChoices = input.enum.split("@@@");
			for(let j = 0; j<iChoices.length; j++){
				tmpChoice = new FlowForm.ChoiceOption({
					label: iChoices[j], 
				}),
				choices.push(tmpChoice);
			}
			//when all options have been created, create a MultipleChoice
			return new FlowForm.QuestionModel({
				examId : exam.examId,
				id: input.id,
				title: input.title,
				helpTextShow: false,
				type: FlowForm.QuestionType.MultipleChoice,
				required: true,
				multiple: false,
				options: choices,
		    });
		}
		
		function createTxtElement(input, exam){
			return new FlowForm.QuestionModel({
				examId : exam.examId,
				id:input.id,
				title: input.title,
				type: FlowForm.QuestionType.LongText,
				required: true
		    });
		}
		
		function createBreakElement(exam){
			return new FlowForm.QuestionModel({
				id : "exam-"+exam.examId +"-break",
				examId : exam.examId,
	            title: exam.examTitle,
	            description: exam.examDescription,
	            type: FlowForm.QuestionType.SectionBreak,
			});
		}
		
	}

	return { render: render };	
})(jQuery);