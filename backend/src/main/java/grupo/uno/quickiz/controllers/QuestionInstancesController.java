package grupo.uno.quickiz.controllers;

import java.util.List;

// import grupo.uno.quickiz.Validation.QuestionValidator;
// import org.springframework.validation.annotation.Validated;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import grupo.uno.quickiz.models.QuestionInstance;
import org.springframework.beans.factory.annotation.Autowired;
import grupo.uno.quickiz.repositories.QuestionInstanceRepository;


@RestController
@RequestMapping("questioninstances")
@CrossOrigin(origins = "*")
public class QuestionInstancesController {

    @Autowired
    private QuestionInstanceRepository questionInstanceRepository;
    // @Autowired
    // private QuestionValidator questionValidator;

    // @InitBinder
    // protected void initBinder(WebDataBinder binder){
        // binder.addValidators(questionValidator);
    // }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<QuestionInstance> getQuestionInstances()
    {
        return questionInstanceRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    public QuestionInstance getQuestionInstanceById(@PathVariable Integer id) {
        return questionInstanceRepository.findById(id).get();
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    // public QuestionInstance createQuestionInstance(@RequestBody @Validated QuestionInstance questionInstance)
    public QuestionInstance createQuestionInstance(@RequestBody QuestionInstance questionInstance)
    {
        return questionInstanceRepository.save(questionInstance);
    }

    @RequestMapping(value="{id}", method = RequestMethod.PUT)
    @ResponseBody
    // public QuestionInstance updateQuestionInstance(@RequestBody @Validated QuestionInstance questionInstance, @PathVariable Integer id) {
    public QuestionInstance updateQuestionInstance(@RequestBody QuestionInstance questionInstance, @PathVariable Integer id)
    {


        System.out.print("\n1");
        // verify if exist
        if(questionInstanceRepository.existsById(id)){
            System.out.print("\n2");
            QuestionInstance q = questionInstanceRepository.findById(id).get();
            System.out.print("\n3");
            System.out.print("\n-------\n");
            System.out.print(questionInstance.getAnswer());
            System.out.print("\n-------");
            q.setAnswer(questionInstance.getAnswer());
            q.setStudentAnswer(questionInstance.getStudentAnswer());
            System.out.print("\n4");
            q.setVariableValues(questionInstance.getVariableValues());
            System.out.print("\n5");
            questionInstanceRepository.save(q);
            System.out.print("\n6");
            return q;
        }
        System.out.print("7");
        return null;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteQuestionInstance(@PathVariable Integer id) {
        // verify if exist
        if(questionInstanceRepository.existsById(id)) {
            questionInstanceRepository.deleteById(id);
        }
    }

    // @RequestMapping(value = "pool/{id}", method = RequestMethod.GET)
    // public List<QuestionInstance> poolQuestionInstances(@PathVariable Integer id) {
        // // verify if exist
        // Pool pool = poolRepository.findById(id).get();
        // return pool.getQuestions();
    // }
}

