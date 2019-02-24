// package grupo.uno.quickiz.controllers;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;

// import grupo.uno.quickiz.models.Quiz;
// import grupo.uno.quickiz.command.HttpRequest;
// import grupo.uno.quickiz.models.Question;
// import grupo.uno.quickiz.models.QuestionInstance;
// import grupo.uno.quickiz.models.QuestionStructure;
// import grupo.uno.quickiz.models.QuizStructure;

// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.*;

// import grupo.uno.quickiz.repositories.QuizRepository;
// import grupo.uno.quickiz.repositories.QuestionRepository;
// import org.springframework.beans.factory.annotation.Autowired;


// @RestController
// @RequestMapping("quizzes")
// @CrossOrigin(origins = "*")
// public class QuizzesController {

//     @Autowired
//     private QuizRepository quizRepository;

//     @Autowired
//     private QuestionRepository questionRepository;
    
//     private HttpRequest httpRequestPythonCode = new HttpRequest();

//     @RequestMapping(value = "", method = RequestMethod.GET)
//     public List<Quiz> getQuizzes()
//     {
//         return quizRepository.findAll();
//     }

//     @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
//     public Quiz getQuizById(@PathVariable Integer id) {

//         Quiz quiz = quizRepository.findById(id).get();
//         System.out.print("\nAntes del for");
//         for (QuestionInstance questionInstance : quiz.getQuestionInstances()) {
//             System.out.print("\nfor\n");
//             System.out.print(questionInstance.getQuestion().getId());
//             questionInstance.setQuestionId(questionInstance.getQuestion().getId());
//             //questionInstance.setQuestion(questionRepository.findById(questionInstance.getQuestion().getId()).get());
//         }
//         System.out.print("\nDespues del for");
//         return quiz;
//     }

//     @RequestMapping(value="", method = RequestMethod.POST)
//     @ResponseStatus(HttpStatus.CREATED)
//     @ResponseBody
//     public Quiz createQuiz(@RequestBody QuizStructure quizStructure)
//     {
//     	Quiz quiz = new Quiz();
//     	quiz.setQuizStructure(quizStructure);
    	
    	
//     	List<QuestionInstance> questionInstances = new ArrayList<QuestionInstance>();
//     	QuestionInstance questionInstance = new QuestionInstance();
    	
//     	for(QuestionStructure questionStructure : quizStructure.getQuestionsStructures()) {
// 			Question question = questionRepository.findQuestion(questionStructure.getScore(), questionStructure.getPool().getId());
// 			questionInstance.setQuestion(question);
			
// 			String[] variables = question.getVariables().split("\n");
// 			String variablesQuestionInstance = "";
// 			String json = "";
// 			for(int i = 0;i < variables.length; i++) {
// 				String[] variableX = variables[i].split("=");
// 				String[] valoresVariablex = variableX[1].split(",");
// 				String chosenValue = valoresVariablex[new Random().nextInt(valoresVariablex.length)];
// 				chosenValue = chosenValue.replaceAll("[\\[\\]]", "");
// 				json = json + variableX[0] + "=" + chosenValue + "\n";
// 				variablesQuestionInstance = variablesQuestionInstance + variableX[0] + "=" + chosenValue;
// 				questionInstance.setVariableValues(variablesQuestionInstance);
// 				//System.out.println(variableX[0]+"--->"+variableX[1]);
	
// 			}
// 			json += question.getCode();
			
// 			String pythonAnswer = httpRequestPythonCode.post("{\"files\": [{\"name\": \"main.py\", \"content\": "+"\""+json+"\""+"}]}");
// 			questionInstance.setAnswer(pythonAnswer);
// 			questionInstance.setQuiz(quiz);
// 			questionInstances.add(questionInstance);
// 			System.out.println(json);
// 			System.out.println(pythonAnswer);
// 			System.out.println();
			
// 			//System.out.println(question.getCode()+"---->"+question.getScore());
// 			//System.out.println();
			
// 		}
//     	quiz.setQuestionInstances(questionInstances);
//     	return quizRepository.save(quiz);
        
//     }

//     @RequestMapping(value="{id}", method = RequestMethod.PUT)
//     public Quiz updateQuiz(@RequestBody Quiz quiz, @PathVariable Integer id) {
//         if(quizRepository.existsById(id)){
//             Quiz q = quizRepository.findById(id).get();
//             q.setStartAt(quiz.getStartAt());
//             q.setFinishAt(quiz.getFinishAt());
//             quizRepository.save(q);
//             return q;
//         }
//         return null;
//     }

//     @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
//     public void deleteQuiz(@PathVariable Integer id) {
//         // verify if exist
//         if(quizRepository.existsById(id)){
//             quizRepository.deleteById(id);
//         }
//     }
// }

