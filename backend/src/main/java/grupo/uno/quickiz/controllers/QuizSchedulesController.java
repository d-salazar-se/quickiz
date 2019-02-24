package grupo.uno.quickiz.controllers;

import java.util.List;

import grupo.uno.quickiz.models.QuizSchedule;
import grupo.uno.quickiz.repositories.QuizScheduleRepository;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;



@RestController
@CrossOrigin(origins = "*")
public class QuizSchedulesController {

    @Autowired
    private QuizScheduleRepository quizScheduleRepository;

    @RequestMapping(value = "/quizSchedules", method = RequestMethod.GET)
    public List<QuizSchedule> getQuizSchedules()
    {
        return quizScheduleRepository.findAll();
    }

    @RequestMapping(value = "/quizSchedules/{id}", method = RequestMethod.GET, produces = "application/json")
    public QuizSchedule getQuizScheduleById(@PathVariable Integer id) {
        if(quizScheduleRepository.findById(id) != null){
            return quizScheduleRepository.findById(id).get();
        }
        return null;
    }

    @RequestMapping(value="/quizSchedules", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public QuizSchedule createQuizSchedule(@RequestBody QuizSchedule quizSchedule, BindingResult result) {
        if(result.hasErrors()){
            return quizSchedule;
        }
        return quizScheduleRepository.save(quizSchedule);
    }

    @RequestMapping(value="/quizSchedules/{id}", method = RequestMethod.PUT)
    public QuizSchedule updateQuizSchedule(@RequestBody QuizSchedule quizSchedule, @PathVariable Integer id) {
        if(quizScheduleRepository.findById(id) != null){
            QuizSchedule q = quizScheduleRepository.findById(id).get();
            q.setStartAt(quizSchedule.getStartAt());
            q.setFinishAt(quizSchedule.getFinishAt());
            quizScheduleRepository.save(q);
            return q;
        }
        return null;
    }

    @RequestMapping(value = "/quizSchedules/{id}", method = RequestMethod.DELETE)
    public void deleteQuizSchedule(@PathVariable Integer id) {
        if(quizScheduleRepository.findById(id) != null){
            quizScheduleRepository.deleteById(id);
        }
    }
}