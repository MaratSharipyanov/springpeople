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

@Controller
public class PeopleController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<User> users = userRepository.findAll();
        model.put("users", users);
        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam(defaultValue = "Введите имя") String name, @RequestParam(defaultValue = "Введите фамилию") String lastname, Map<String, Object> model) {
        User user = new User(name, lastname);
        String message = "Сотрудник " + user.toString() + " добавлен";
        userRepository.save(user);
        model.put("message", message);
        return main(model);
    }

    @PostMapping("/find")
    public String find(@RequestParam String filter, Map<String, Object> model) {
        if (filter != null && !filter.isEmpty()) {
            List<User> findFilter = userRepository.findByLastnameContainingOrNameContaining(filter, filter);
            model.put("users", findFilter);
            return "main";
        } else return main(model);
    }

    @PostMapping("/update")
    public String updateData(@RequestParam (required = false) Integer id, @RequestParam(required = false) String updname, @RequestParam(required = false) String updlastname,
                             Map<String, Object> model) {
        if (id == null) {
            return main(model);
        } else {
            User updUser = null;
            try {
                updUser = userRepository.findById(id).get();
                String message = updUser.toString();
                if (updname != null && !updname.isEmpty()) {
                    updUser.setName(updname);
                }
                if (updlastname != null && !updlastname.isEmpty()) {
                    updUser.setLastname(updlastname);
                }
                message += " изменён на " + updUser.toString();
                userRepository.save(updUser);
                model.put("message", message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return main(model);
        }
    }

    @PostMapping("/delete")
    public String deleteData(@RequestParam(required = false) Integer delid, Map<String, Object> model) {
        if (delid != null && delid.describeConstable().isPresent()) {
            try {
                User delUser = userRepository.findById(delid).get();
                String message = "Сотрудник " + delUser.toString() + " удалён";
                userRepository.delete(delUser);
                model.put("message", message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }return main(model);
    }
}