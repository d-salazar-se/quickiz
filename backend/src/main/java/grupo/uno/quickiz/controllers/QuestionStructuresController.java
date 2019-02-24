package grupo.uno.quickiz.controllers;

import java.util.List;
import grupo.uno.quickiz.models.Pool;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import grupo.uno.quickiz.models.QuestionStructure;
import grupo.uno.quickiz.models.QuizStructure;
import grupo.uno.quickiz.repositories.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import grupo.uno.quickiz.repositories.QuizStructureRepository;
import grupo.uno.quickiz.repositories.QuestionStructureRepository;


@RestController
@RequestMapping("questionstructures")
@CrossOrigin(origins = "*")
public class QuestionStructuresController {

    @Autowired
    private QuestionStructureRepository questionStructureRepository;
    @Autowired
    private PoolRepository poolRepository;
    @Autowired
    private QuizStructureRepository quizStructureRepository;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<QuestionStructure> getQuestionStructures()
    {
        return questionStructureRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    public QuestionStructure getQuestionStructureById(@PathVariable Integer id) {
        return questionStructureRepository.findById(id).get();
    }

    // @RequestMapping(value="", method = RequestMethod.POST)
    // @ResponseStatus(HttpStatus.CREATED)
    // @ResponseBody
    // public QuestionStructure createQuestionStructure(@RequestBody QuestionStructure questionStructure)
    // {
    //     Pool p = poolRepository.findById(questionStructure.getPoolId()).get();
    //     questionStructure.setPool(p);

    //     QuizStructure q = quizStructureRepository.findById(questionStructure.getQuizStructureId()).get();
    //     questionStructure.setQuizStructure(q);

    //     return questionStructureRepository.save(questionStructure);
    // }

    // @RequestMapping(value="{id}", method = RequestMethod.PUT)
    // public QuestionStructure updateQuestionStructure(@RequestBody QuestionStructure questionStructure, @PathVariable Integer id) {
    //     if(questionStructureRepository.existsById(id)){
    //         QuestionStructure q = questionStructureRepository.findById(id).get();
    //         q.setScore(questionStructure.getScore());
    //         q.setPool(poolRepository.findById(questionStructure.getPoolId()).get());
    //         questionStructureRepository.save(q);
    //         return q;
    //     }
    //     return null;
    // }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteQuestionStructure(@PathVariable Integer id) {
        // verify if exist
        if(questionStructureRepository.existsById(id)){
            questionStructureRepository.deleteById(id);
        }
    }

    @RequestMapping(value = "pool/{id}", method = RequestMethod.GET)
    public List<QuestionStructure> poolQuestionStructures(@PathVariable Integer id) {
        // verify if exist
        if(poolRepository.existsById(id)){
            Pool pool = poolRepository.findById(id).get();
            return pool.getQuestionStructures();
        }
        return null;
    }
}

