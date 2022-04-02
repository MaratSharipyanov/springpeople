package com.example.springpeople;

import com.example.springpeople.domain.User;
import com.example.springpeople.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class GreetingController {

   @Autowired
    private UserRepository userRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }
    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<User> users = userRepository.findAll();
        model.put("users", users);
        return "main";
    }
    @PostMapping
    public String add(@RequestParam String name, @RequestParam String lastname, Map<String, Object> model) {
        User user = new User(name, lastname);
        userRepository.save(user);
        return main(model);
    }
    @PostMapping("find")
    public String find(@RequestParam String filter, Map<String, Object> model) {
        if (filter != null && !filter.isEmpty()) {
            List<User> findFilter = userRepository.findByLastnameContainingOrNameContaining(filter, filter);
            model.put("users", findFilter);
            return "main";
        } else return main(model);
    }
    @PostMapping("update")
    public String updateData(@RequestParam Integer id, @RequestParam String updname, @RequestParam String updlastname,
                             Map<String, Object> model) {
        Optional<User> updUsers = userRepository.findById(id);
        User updUser = updUsers.get();
        updUser.setName(updname);
        updUser.setLastname(updlastname);
        userRepository.save(updUser);
        model.put("users", updUser);
        return "main";
    }
}
