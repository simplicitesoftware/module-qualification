var QualPostCertif = QualPostCertif || (function() {
	var app = { list: null, item: null };

	/**
	 * Render
	 * 
	 * @param params
	 *            Parameters
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
			let usrExObjIds = params.userExamIds;
			
			if("" == exams){
				unknown = true;
				start =  new FlowForm.QuestionModel({
					id: 'start',
					title: "Oups... ",
					content: "Il semblerait y avoir un problème avec l'affichage de votre questionnaire. Il est possible que votre token soit inexistant ou ait expiré.",
					type: FlowForm.QuestionType.SectionBreak,
					required: false,
				});
				output.push(start);
			}
			
			else{
				
				console.log("EXAMS :");
				console.log(exams);
				start = new FlowForm.QuestionModel({
		            id: 'start',
		            tagline:"Bonjour "+String.fromCodePoint("0x1F44B"),
		            title: "Bienvenue sur le portail d'évaluation post-certification Simplicité.",
	            	description:"Lorsque vous êtes prêts, cliquez sur 'suivant' ou Appuyez sur la touche ENTRÉE pour commencer.",
		            type: FlowForm.QuestionType.SectionBreak,
		            required: true,
        		});
        	
				output.push(start);
				
				let examChoices = [];
				let examJumps = {};
				for(let j = 0; j<exams.length; j++){
					let examTitle = exams[j].examTitle;
					let examId = "exam-"+exams[j].examId +"-break";
					let tmpChoice = new FlowForm.ChoiceOption({
						label: examTitle, 
						value: examId,
					});
					examJumps[examId] = examId;
					examChoices.push(tmpChoice);
				}
				let examSelector = new FlowForm.QuestionModel({
					id:"exam_selector",
					title : "Sélectionnez une catégorie à évaluer :",
					type: FlowForm.QuestionType.Dropdown,
		            multiple: false,
		            inline: true,
					required: true,
					options: examChoices,
					jump: examJumps,
				});
				let endExamChoices = examChoices;
				let tmpChoice = new FlowForm.ChoiceOption({
					label: "Terminer l\'évaluation", 
					value: "other",
				});
				endExamChoices.push(tmpChoice);
				
				let endExamJumps = examJumps;
				endExamJumps.other = "_submit";
				
				output.push(examSelector);
				
				for(let k = 0; k < exams.length; k++){
					let input = exams[k].questions;
					let examTitle = exams[k].examTitle;
					for(let i = 0; i < input.length; i++){
						if(input[i].type == "ENUM"){
							tmp = createEnumElement(input[i], exams[k]);
						}
						else if(input[i].type == "TXT"){
							tmp = createTxtElement(input[i], exams[k]);
						}
						else if(input[i].type == "QST_BREAK"){
							tmp = new FlowForm.QuestionModel({
								id : "exam-"+exams[k].examId +"-break",
								examId : exams[k].examId,
					            title: examTitle,
					            description: exams[k].examDescription,
					            type: FlowForm.QuestionType.SectionBreak,
			        		});
						}
						else if(input[i].type == "QST_BREAK_END"){
							tmp = new FlowForm.QuestionModel({
								id:"exam_selector",
								title : "Sélectionnez une catégorie à évaluer :",
								type: FlowForm.QuestionType.Dropdown,
					            multiple: false,
					            inline: true,
								required: true,
								options: endExamChoices,
								jump: examJumps,
			        		});
						}
						
						
						output.push(tmp);
					}
				}
			}
			
			console.log(output);
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
						scores:[],
						totalScores : {},
					}
				},
				
				methods: {
				
				 onAnswer(qA) {
					if(qA.type !== FlowForm.QuestionType.SectionBreak && qA.id !== "exam_selector" && !(qA.id).includes("break_end")){
						// question answered -> set value in back
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
							qualExusrUsrexamId:qA.examId,
							qualExusrExamexId__qualExamexExId__qualExId:qA.id
						});
						
					}
				},
					
			      onComplete(completed, questionList) {
			        // This method is called whenever the "completed" status is
					// changed.
			        this.completed = completed
			        if(this.generic){
			        	this.onQuizSubmit();
			        }
			      },
			      
			      onQuizSubmit() {
			        // Set `submitted` to true so the form knows not to allow
					// back/forward
			        // navigation anymore.
			        this.$refs.flowform.submitted = true
			        
			        this.submitted = true
			        
			        if(!unknown){
			        	this.validateExams(this.validateCbk);
			        	console.log("validation")
			        	exams.forEach(exam => {
			        		let examQuestions = [];
			        		this.questions.forEach(qst => {
			        			if(qst.examId == exam.examId && qst.type !== FlowForm.QuestionType.SectionBreak){
			        				examQuestions.push(qst);
			        			}
			        		});
			        		if(!generic){
			        			/*
								 * let examScore = this.calculateScore(exam,
								 * examQuestions); this.scores.push(examScore);
								 */
			        		}
			        	})
			        }
			      },
			      
			      calculateScore(exam, qsts){
			    	let score = 0;
			      	let total = qsts.length;
			      	qsts.forEach(qst =>{
			      		let answer = qst.answer;
			      		let correctAnswer = exam.answers[qst.id];
			      		if (answer > correctAnswer) {
			              score = score + correctAnswer;
			            }
			            else {
			            	score = score + answer;
			            }
			      	});
			      	// score = Math.round((score / total)*100);
			      	return {"examId": exam.examId, "examTitle": exam.examTitle, "score": score, "total":total};
			      	// each exam has a specific score;
			      },
			
				validateExams(validateCbk){
					this.loading = true;
					let examScores = [];
			      	// set ended for all exams
		    		var obj = app.getBusinessObject("QualUserExam");
	    			obj.resetFilters();
			      	usrExObjIds.forEach(function(id, index) {
						obj.getForUpdate(function(){
							obj.item.qualUsrexamEtat = "DONE";
							obj.update(function(){
								// add score to json
								examScores.push({
									"examId": id, 
									"examTitle": obj.item.qualUsrexamExamId__qualExamName, 
									"score": obj.item.qualUsrexamScore, 
									"total":obj.item.qualUsrexamTotalPoints
								});
								if(index === usrExObjIds.length - 1){
									// all exams have been score, display score
									validateCbk(examScores);
								}
							});
						},id);
					});
					
					// display "wait" section
					setTimeout(() => {
						this.loading = false;
					}, 1000)
					
				},
				
				validateCbk(usrScores){
					
					let testTotal = 0;
					let userTotal = 0;
					console.log(usrScores);
					usrScores.forEach(elt =>{
						testTotal += elt.total;
						userTotal += elt.score;
					})
					
					this.totalScores = {
						"userScoreTotal":userTotal,
						"testScoreTotal":testTotal,
					};
					
					console.log(this.totalScores);
					
					this.scores = usrScores;
					this.scored = true;
				}
				
			      
			    },
			    
			});
			 
		} catch(e) {
			console.error('Render error: ' + e.message);
		}
		
		function createEnumElement(input, exam){
			
			let choices = [];
			let iChoices = input.enum.split("@@@");
			for(let j = 0; j<iChoices.length; j++){
				let tmpChoice = new FlowForm.ChoiceOption({
					label: iChoices[j],
				});
				choices.push(tmpChoice);
			}
			
			return new FlowForm.QuestionModel({
				examId : exam.examId,
				id: input.id,
				title: input.title,
				helpTextShow: false,
				type: FlowForm.QuestionType.Dropdown,
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
		
	}

	return { render: render };	
})();
