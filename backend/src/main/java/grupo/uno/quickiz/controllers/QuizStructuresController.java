package grupo.uno.quickiz.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import grupo.uno.quickiz.models.QuizStructure;
import grupo.uno.quickiz.models.QuestionStructure;
import grupo.uno.quickiz.command.HttpRequest;
import grupo.uno.quickiz.models.Pool;
import org.springframework.web.bind.annotation.*;
import grupo.uno.quickiz.repositories.QuizStructureRepository;
import grupo.uno.quickiz.repositories.QuestionStructureRepository;
import grupo.uno.quickiz.repositories.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("quizstructures")
@CrossOrigin(origins = "*")
public class QuizStructuresController {

    @Autowired
    private QuizStructureRepository quizStructureRepository;

    @Autowired
    private QuestionStructureRepository questionStructureRepository;

    @Autowired
    private PoolRepository poolRepository;
    
    

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<QuizStructure> getQuizStructures()
    {
        return quizStructureRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    public QuizStructure getQuizStructureById(@PathVariable Integer id) {
        if (quizStructureRepository.existsById(id)) {
            return quizStructureRepository.findById(id).get();
        }
        return null;
    }

    @RequestMapping(value="", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public QuizStructure createQuizStructure(@RequestBody ObjectNode json)
    {
        QuizStructure quiz = new QuizStructure(
                                    json.get("quiz").get("name").asText(),
                                    json.get("quiz").get("duration").asInt(),
                                    json.get("quiz").get("slacktime").asInt()
                                );

        quiz = quizStructureRepository.save(quiz);

        Pool pool;
        QuestionStructure question;
        Integer score, poolId;
        final JsonNode questions = json.get("questions");

        for (final JsonNode q : questions) {
            score = q.get("score").asInt();
            poolId = q.get("poolId").asInt();

            pool = poolRepository.findById(poolId).get();
            
            question = new QuestionStructure(score, pool);
            question.setQuizStructure(quiz);
            questionStructureRepository.save(question);
        }

        return quiz;
    }

    @RequestMapping(value="{id}", method = RequestMethod.PUT)
    public QuizStructure updateQuizStructure(@RequestBody ObjectNode json, @PathVariable Integer id) {
        if(quizStructureRepository.existsById(id)){
            System.out.println(json);
            QuizStructure quiz = quizStructureRepository.findById(id).get();
            quiz.setName(json.get("quiz").get("name").asText());
            quiz.setDuration(json.get("quiz").get("duration").asInt());
            quiz.setSlackTime(json.get("quiz").get("slacktime").asInt());
            quizStructureRepository.save(quiz);

            for (QuestionStructure q: quiz.getQuestionsStructures()) {
                questionStructureRepository.deleteById(q.getId());
            }

            Pool pool;
            Integer score, poolId;
            final JsonNode questions = json.get("questions");
            QuestionStructure question;

            for (final JsonNode q : questions) {
                score = q.get("score").asInt();
                poolId = q.get("poolId").asInt();

                pool = poolRepository.findById(poolId).get();
                
                question = new QuestionStructure(score, pool);
                question.setQuizStructure(quiz);
                questionStructureRepository.save(question);
            }

            return quiz;
        }
        return null;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteQuizStructure(@PathVariable Integer id) {
        if (quizStructureRepository.existsById(id)) {
            quizStructureRepository.deleteById(id);
        }
    }
}

