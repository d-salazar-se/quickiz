package grupo.uno.quickiz.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import grupo.uno.quickiz.models.Quiz;
import grupo.uno.quickiz.command.HttpRequest;
import grupo.uno.quickiz.models.Question;
import grupo.uno.quickiz.models.QuestionInstance;
import grupo.uno.quickiz.models.QuestionStructure;
import grupo.uno.quickiz.models.QuizStructure;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import grupo.uno.quickiz.repositories.QuizRepository;
import grupo.uno.quickiz.repositories.QuizStructureRepository;
import grupo.uno.quickiz.repositories.QuestionRepository;
import grupo.uno.quickiz.repositories.QuestionInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("quizzes")
@CrossOrigin(origins = "*")
public class QuizzesController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private QuestionInstanceRepository questionInstanceRepository;
    
    @Autowired
    private QuizStructureRepository quizStructureRepository;

    private HttpRequest httpRequestPythonCode = new HttpRequest();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Quiz> getQuizzes()
    {
        return quizRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    public Quiz getQuizById(@PathVariable Integer id) {

        Quiz quiz = quizRepository.findById(id).get();
        return quiz;
    }

    @RequestMapping(value="{id}/generate", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Quiz createQuiz(@PathVariable Integer id)
    {
        QuizStructure quizStructure = quizStructureRepository.findById(id).get();
    	Quiz quiz = new Quiz();
    	quiz.setQuizStructure(quizStructure);
    	quizRepository.save(quiz);
    	
    	List<QuestionInstance> questionInstances = new ArrayList<QuestionInstance>();
    	QuestionInstance questionInstance = new QuestionInstance();
    	
    	for(QuestionStructure questionStructure : quizStructure.getQuestionsStructures()) {
			Question question = questionRepository.findQuestion(questionStructure.getScore(), questionStructure.getPool().getId());
			questionInstance.setQuestion(question);
			
			String[] variables = question.getVariables().split("\n");
			String variablesQuestionInstance = "{";
			String json = "";
			for(int i = 0;i < variables.length; i++) {
				String[] variableX = variables[i].split("=");
				String[] valoresVariablex = variableX[1].split(",");
				String chosenValue = valoresVariablex[new Random().nextInt(valoresVariablex.length)];
				chosenValue = chosenValue.replaceAll("[\\[\\]]", "");
				json = json + variableX[0] + "=" + chosenValue + "\n";
                variablesQuestionInstance = variablesQuestionInstance + "\""+variableX[0]+"\"" + ":" + chosenValue + ",";

				System.out.println(variableX[0]+"--->"+variableX[1]);

			}

			json += question.getCode();
            variablesQuestionInstance = variablesQuestionInstance.substring(0, (variablesQuestionInstance.length()-1));
            variablesQuestionInstance = variablesQuestionInstance + "}";
            questionInstance.setVariableValues(variablesQuestionInstance);
			String[] pythonAnswer = httpRequestPythonCode.post("{\"files\": [{\"name\": \"main.py\", \"content\": "+"\""+json+"\""+"}]}").split("[\"],[\"]");
			String[] stdout = pythonAnswer[0].split("[\"]:[\"]");
            String answer = stdout[1];
            answer = answer.substring(0,(answer.length()-1));
            answer = answer.substring(0,(answer.length()-1));
            questionInstance.setVariableValues(variablesQuestionInstance);
			questionInstance.setAnswer(answer);
			questionInstance.setQuiz(quiz);
            questionInstance = questionInstanceRepository.save(questionInstance);
			questionInstances.add(questionInstance);
		}
    	quiz.setQuestionInstances(questionInstances);
    	return quizRepository.save(quiz);
        
    }

    @RequestMapping(value="{id}", method = RequestMethod.PUT)
    public Quiz updateQuiz(@RequestBody Quiz quiz, @PathVariable Integer id) {
        if(quizRepository.existsById(id)){
            Quiz q = quizRepository.findById(id).get();
            q.setStartAt(quiz.getStartAt());
            q.setFinishAt(new Timestamp(System.currentTimeMillis()));

            QuestionInstance questionInstance;
            Integer score = 0;
            for (QuestionInstance qi : quiz.getQuestionInstances()) {
                questionInstance = questionInstanceRepository.findById(qi.getId()).get();
                questionInstance.setStudentAnswer(qi.getStudentAnswer());
                questionInstanceRepository.save(questionInstance);

                String correctAnswer = questionInstance.getAnswer();
                String studentAnswer = questionInstance.getStudentAnswer();
                System.out.println("correctAnswer: " + correctAnswer + "-- studentAnswer: " + studentAnswer);
                if (Objects.equals(correctAnswer, studentAnswer)) {
                    score += questionInstance.getQuestion().getScore();
                }
            }

            q.setScore(score);

            quizRepository.save(q);
            return q;
        }
        return null;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteQuiz(@PathVariable Integer id) {
        // verify if exist
        if(quizRepository.existsById(id)){
            quizRepository.deleteById(id);
        }
    }
}

