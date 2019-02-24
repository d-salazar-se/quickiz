package grupo.uno.quickiz.controllers;

import java.util.List;

import grupo.uno.quickiz.models.User;
import org.springframework.http.HttpStatus;
import grupo.uno.quickiz.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@CrossOrigin(origins = "*")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUser()
    {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = "application/json")
    public User getUserById(@PathVariable Integer id) {
        return userRepository.findById(id).get();
    }

    @RequestMapping(value="/users", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User createUser(@RequestBody User user)
    {
        return userRepository.save(user);
    }

    @RequestMapping(value="/users/{id}", method = RequestMethod.PUT)
    public User updateUser(@RequestBody User user, @PathVariable Integer id) {
        if(userRepository.existsById(id)){
            User u = userRepository.findById(id).get();
            u.setName(user.getName());
            u.setEmail(user.getEmail());
            userRepository.save(u);
            return u;
        }
        // use set and get role
        // u.setRole_id(user.getRole().id);
        return null;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Integer id) {
        // verify if exist
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
    }

}

