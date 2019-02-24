package grupo.uno.quickiz.controllers;

import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import grupo.uno.quickiz.designPatterns.QuestionLogger;
import grupo.uno.quickiz.models.Pool;
import grupo.uno.quickiz.models.Question;
import org.springframework.http.HttpStatus;
import grupo.uno.quickiz.command.HttpRequest;
import org.springframework.web.bind.annotation.*;
import grupo.uno.quickiz.repositories.PoolRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import grupo.uno.quickiz.repositories.QuestionRepository;
import grupo.uno.quickiz.repositories.QuizStructureRepository;

import org.springframework.beans.factory.annotation.Autowired;

// import org.springframework.web.bind.WebDataBinder;
// import grupo.uno.quickiz.Validation.QuestionValidator;
// import org.springframework.validation.annotation.Validated;


@RestController
@RequestMapping("questions")
@CrossOrigin(origins = "*")
public class QuestionsController {

    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private PoolRepository poolRepository;
    
    @Autowired
    private QuizStructureRepository quizStructureRepository;
    
    // @Autowired
    // private QuestionValidator questionValidator;

    // @InitBinder
    // protected void initBinder(WebDataBinder binder){
    // binder.addValidators(questionValidator);
    // }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Question> getQuestions()
    {
        return questionRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    public Question getQuestionById(@PathVariable Integer id) {
        return questionRepository.findById(id).get();
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    // public Question createQuestion(@RequestBody @Validated Question question)
    public Question createQuestion(@RequestBody ObjectNode json)
    //public Question createQuestion(@RequestBody Question question)
    {
        HttpRequest httpRequestPythonCode = new HttpRequest();
        Random ran = new Random();
        String code = json.get("question").get("code").asText();
        String varCode = "";
        JsonNode jsonVar = json.get("variables");
        Set<String> vars = new HashSet<>();
        Iterator<String> keys = jsonVar.fieldNames();
        while(keys.hasNext()){
            vars.add(keys.next());
        }
        for(String field: vars){
            int large = jsonVar.get(field).size();
            int index = ran.nextInt(large);
            JsonNode variable = jsonVar.get(field).get(index);
            varCode = varCode + field + "=" + variable.toString().replaceAll("[ \"\" ]", "'") + "\n";
        }
        String codigo = varCode + code;
        String jsonPost = "{\"files\": [{\"name\": \"main.py\", \"content\": "+"\""+codigo+"\""+"}]}";
        System.out.println(jsonPost);
        String respuesta = httpRequestPythonCode.post(jsonPost);
        System.out.println(respuesta);
        String[] request = respuesta.split("[\"],[\"]");
        System.out.println("dasd " + request[2]);
        System.out.println("dasd " + request[2].length());
        //String[] error = request[2].split(":");
        System.out.println(request[2]);
        if(request[2].length() == 11){
            System.out.println("El codigo no contiene error.");
            Question question = new Question();
            question.setCode(json.get("question").get("code").asText());
            question.setTitle(json.get("question").get("title").asText());
            question.setVariables(json.get("question").get("variables").asText());
            //QuestionLogger questionLogger = QuestionLogger.getInstance();
            int idPool = json.get("question").get("poolId").asInt();
            Pool p = poolRepository.findById(idPool).get();
            question.setPool(p);
            return questionRepository.save(question);
        }
        else{
            System.out.println("El codigo contiene errores");
            return null;
        }
        /*QuestionLogger questionLogger = QuestionLogger.getInstance();
        Pool p = poolRepository.findById(question.getPoolId()).get();

    	QuestionLogger questionLogger = QuestionLogger.getInstance();
        Pool p = poolRepository.findById(question.getPool().getId()).get();

        question.setPool(p);
        questionLogger.logQuestion(question.getTitle(), question.getCode(), question.getVariables(), p.getName(), new Timestamp(System.currentTimeMillis()) );
        return questionRepository.save(question); */
        //Question question = new Question(json.get("question").get("").asInt(), )
    }

    @RequestMapping(value="{id}", method = RequestMethod.PUT)
    // public Question updateQuestion(@RequestBody @Validated Question question, @PathVariable Integer id) {
    public Question updateQuestion(@RequestBody Question question, @PathVariable Integer id) {
        if(questionRepository.existsById(id)){
            Question q = questionRepository.findById(id).get();
            q.setTitle(question.getTitle());
            q.setPool(poolRepository.findById(question.getPool().getId()).get());
            q.setQuizStructure(quizStructureRepository.findById(question.getQuizStructure().getId()).get());
            questionRepository.save(q);
            return q;
        }
        return null;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteQuestion(@PathVariable Integer id) {

        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
        }
    }

    @RequestMapping(value = "pool/{id}", method = RequestMethod.GET)
    public List<Question> poolQuestions(@PathVariable Integer id) {
        if(poolRepository.existsById(id)){
            Pool pool = poolRepository.findById(id).get();
            return pool.getQuestions();
        }
        return null;
    }
}

