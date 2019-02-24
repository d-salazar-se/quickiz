package grupo.uno.quickiz.controllers;

import java.util.List;

import grupo.uno.quickiz.models.Pool;
import org.springframework.http.HttpStatus;
import grupo.uno.quickiz.repositories.PoolRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;



@RestController
@CrossOrigin(origins = "*")
public class PoolsController {

    @Autowired
    private PoolRepository poolRepository;

    

    @RequestMapping(value = "/pools", method = RequestMethod.GET)
    public List<Pool> getPools()
    {
        return poolRepository.findAll();
    }

    @RequestMapping(value = "/pools/{id}", method = RequestMethod.GET, produces = "application/json")
    public Pool getPoolById(@PathVariable Integer id) {
        return poolRepository.findById(id).get();
    }

    @RequestMapping(value="/pools", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Pool createPool(@RequestBody Pool pool, BindingResult result) {
        if(result.hasErrors()){
            return pool;
        }
        return poolRepository.save(pool);
    }

    @RequestMapping(value="/pools/{id}", method = RequestMethod.PUT)
    public Pool updatePool(@RequestBody Pool pool, @PathVariable Integer id) {
        if(poolRepository.existsById(id)){
            Pool p = poolRepository.findById(id).get();
            p.setName(pool.getName());
            poolRepository.save(p);
            return p;
        }
        return null;
    }

    @RequestMapping(value = "/pools/{id}", method = RequestMethod.DELETE)
    public void deletePool(@PathVariable Integer id) {
        // verify if exist
        if(poolRepository.existsById(id)){
            poolRepository.deleteById(id);
        }
    }
}
