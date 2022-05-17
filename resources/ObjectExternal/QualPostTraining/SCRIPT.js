var QualPostTraining = QualPostTraining || (function($) {
    var app;

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
            let exObjRowId = "";
            let usrExObjIds = [];
            let examScoresTab = [];

            if ("" == exams) {
                unknown = true;
                start = new FlowForm.QuestionModel({
                    id: 'start',
                    title: "Oups... ",
                    content: "Il semblerait y avoir un problème avec l'affichage de votre questionnaire. Il est possible que votre token soit inexistant ou ait expiré.",
                    type: FlowForm.QuestionType.SectionBreak,
                    required: false,
                })
                output.push(start);
            } else {

                //Creates a 'SectionBreak' element that greets the user before starting an exam
                start = new FlowForm.QuestionModel({
                	id : 'start',
                	title : params.lang.start.title + " " +String.fromCodePoint("0x1F44B"), 
                	subtitle : params.lang.start.subtitle,
                	description: params.lang.start.description,
                    type: FlowForm.QuestionType.SectionBreak,
                    required: true,
                })

                //Add the SectionBreak to the pile of elements to display
                output.push(start);

                //For each exam
                for (let k = 0; k < exams.length; k++) {
                    //get questions of exam
                    let input = exams[k].questions;
                    let examTitle = exams[k].examTitle;
                    //for each question, create relevant type of question type
                    for (let i = 0; i < input.length; i++) {
                        if (input[i].type == "ENUM") {
                            tmp = createEnumElement(input[i], exams[k]);
                        } else if (input[i].type == "MULTI_ENUM") {
                            tmp = createMultiEnumElement(input[i], exams[k]);
                        } else if (input[i].type == "TXT") {
                            tmp = createTxtElement(input[i], exams[k]);
                        } else if (input[i].type == "QST_BREAK") {
                            tmp = createBreakElement(exams[k]);
                        }

                        //add element to output (pile of questions)
                        output.push(tmp);
                    }
                }
            }

			let languageParams = new FlowForm.LanguageModel({
                enterKey: params.lang.ffModel.enterKey,
                shiftKey: params.lang.ffModel.shiftKey,
                continue: params.lang.ffModel.continue,
                pressEnter: params.lang.ffModel.pressEnter,
                otherPrompt: params.lang.ffModel.otherPrompt,
                submitText: params.lang.ffModel.submitText,
                placeholder: params.lang.ffModel.placeholder,
                percentCompleted: params.lang.ffModel.percentCompleted,
                multipleChoiceHelpTextSingle: params.lang.ffModel.multipleChoiceHelpTextSingle,
                longTextHelpText: params.lang.ffModel.longTextHelpText
            });
			
            new Vue({
                el: '#app',
                template: qualTemplate,
                data: function() {
                    return {
                        loading: false,
                        scored: false,
                        submitted: false,
                        completed: false,
                        language: languageParams,
                        questions: output,
                        isValid: !unknown,
                        generic: params.generic,
                        params: params
                    }
                },

                methods: {

                    onAnswer(qA) {
                        //When a sectionbreak is passed and the id associated to it contains "exam", it's that a user has started an exam.
                        //The row is created in the backend
                        if (qA.type == FlowForm.QuestionType.SectionBreak && qA.id.includes("exam")) {
                            var usrExObj = app.getBusinessObject("QualUserExam");
                            usrExObj.resetFilters();
                            usrExObj.getForCreate(function() {
                                usrExObj.item.qualUsrexamUsrId = params.userId;
                                usrExObj.item.qualUsrexamExamId = qA.examId;
                                usrExObj.create(function() {
                                    //store the exams row id
                                    exObjRowId = usrExObj.getRowId();
                                    //add the rowid to the list of exam rowids
                                    usrExObjIds.push(exObjRowId);
                                }, usrExObj.item);

                            });
                        }

                        if (qA.type !== FlowForm.QuestionType.SectionBreak) {
                            //if the type is other than a sectionbreak, it's a question -> set value in back
                            //if multiple, answers will be an array
                            let submittedValue = qA.multiple ? getAnwserFormArray(qA.answer) : qA.answer;
                            var usrAnswerObj = app.getBusinessObject("QualExUsr");
                            usrAnswerObj.resetFilters();
                            usrAnswerObj.search(function() {
                                usrAnswerObj.getForUpdate(function() {
                                    usrAnswerObj.item.qualExusrSubmitted = true;
                                    usrAnswerObj.item.qualExusrAnswer = submittedValue;
                                    usrAnswerObj.update();
                                }, usrAnswerObj.list[0].row_id);
                            }, {
                                qualExusrUsrexamId: exObjRowId,
                                qualExusrExamexId__qualExamexExId__qualExId: qA.id
                            });

                        }
                    },

                    onComplete(completed, questionList) {
                        // This method is called whenever the "completed" status is changed.
                        this.completed = completed
                        //if the type of user is 'generic', the quizz is automatically submitted. If not, it is done by a user action
                        if (this.generic) {
                            this.onQuizSubmit();
                        }
                    },

                    onQuizSubmit() {
                        // Set `submitted` to true so the form knows not to allow back/forward
                        // navigation anymore.
                        this.$refs.flowform.submitted = true
                        this.submitted = true

                        if (!unknown) {
                            this.validateExams();

                            //iterate through each exam
                            exams.forEach(exam => {
                                let examQuestions = [];
                                //get all questions of exam
                                this.questions.forEach(qst => {
                                    if (qst.examId == exam.examId && qst.type !== FlowForm.QuestionType.SectionBreak) {
                                        examQuestions.push(qst);
                                    }
                                });
                            })
                        	 this.scored = true;
                        	 this.scores = examScoresTab;
                        }
                    },

                    //Set status "DONE" on every exam passed by the user
                    validateExams() {
                        this.loading = true;
                        var obj = app.getBusinessObject("QualUserExam");
                        obj.resetFilters();
                        usrExObjIds.forEach(function(id) {
                            obj.getForUpdate(function() {
                                obj.item.qualUsrexamEtat = "DONE";
                                obj.update(function(){
                                	examScoresTab.push({
	                                    "examId": id,
	                                    "examTitle": obj.item.qualUsrexamExamId__qualExamName,
	                                    "score": obj.item.qualUsrexamScore,
	                                    "total": obj.item.qualUsrexamTotalPoints
	                                });
	                                console.log(examScoresTab)
                                });
                                
                            }, id);
                        });

                        //display "wait" section
                        setTimeout(() => {
                            this.loading = false;
                        }, 1000)

                    },

                    store(exam, qsts) {

                        this.loading = true;

                        //display "wait" section
                        setTimeout(() => {
                            this.loading = false;
                        }, 1000)

                    },
                },

            });

        } catch (e) {
            console.error('Render error: ' + e.message);
        }

        function createEnumElement(input, exam) {
            //create choice options
            let choices = [];
            let iChoices = input.enum.split("@@@");
            for (let j = 0; j < iChoices.length; j++) {
                let tmpChoice = new FlowForm.ChoiceOption({
                    label: iChoices[j],
                });
                choices.push(tmpChoice);
            }
            //when all options have been created, create a MultipleChoice
            return new FlowForm.QuestionModel({
                examId: exam.examId,
                id: input.id,
                title: input.title,
                helpTextShow: false,
                type: FlowForm.QuestionType.MultipleChoice,
                required: true,
                multiple: false,
                options: choices,
            });
        }

        function createMultiEnumElement(input, exam) {
            //create choice options
            let choices = [];
            let iChoices = input.enum.split("@@@");
            for (let j = 0; j < iChoices.length; j++) {
                let tmpChoice = new FlowForm.ChoiceOption({
                    label: iChoices[j],
                });
                choices.push(tmpChoice);
            }
            //when all options have been created, create a MultipleChoice
            return new FlowForm.QuestionModel({
                examId: exam.examId,
                id: input.id,
                title: input.title,
                helpTextShow: false,
                type: FlowForm.QuestionType.MultipleChoice,
                required: true,
                multiple: true,
                options: choices,
            });
        }

        function createTxtElement(input, exam) {
            return new FlowForm.QuestionModel({
                examId: exam.examId,
                id: input.id,
                title: input.title,
                type: FlowForm.QuestionType.LongText,
                required: true
            });
        }

        function createBreakElement(exam) {
            return new FlowForm.QuestionModel({
                id: "exam-" + exam.examId + "-break",
                examId: exam.examId,
                title: exam.examTitle,
                description: exam.examDescription,
                type: FlowForm.QuestionType.SectionBreak,
            });
        }

        function getAnwserFormArray(aArray) {
            return aArray.toString().replaceAll(",", "@@@");
        }

    }

    return {
        render: render
    };
})(jQuery);