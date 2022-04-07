package com.example.springpeople.controller;

import com.example.springpeople.domain.Person;
import com.example.springpeople.repos.PersonRepository;
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
    private PersonRepository personRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false) String filter, Map<String, Object> model) {
        if (filter != null && !filter.isEmpty()) {
            List<Person> findFilterUsers = personRepository.findByLastnameContainingOrNameContaining(filter, filter);
            model.put("users", findFilterUsers);
            model.put("filter", filter);
            return "main";
        } else {
            Iterable<Person> users = personRepository.findAll();
            model.put("users", users);
            return "main";
        }
    }

    @PostMapping("/main")
    public String add(@RequestParam(defaultValue = "Введите имя") String name, @RequestParam(defaultValue = "Введите фамилию") String lastname, Map<String, Object> model) {
        Person person = new Person(name, lastname);
        String message = "Сотрудник " + person.toString() + " добавлен";
        personRepository.save(person);
        model.put("message", message);
        Iterable<Person> users = personRepository.findAll();
        model.put("users", users);
        return "main";
    }

    @PostMapping("/update")
    public String updateData(@RequestParam (required = false) Integer id, @RequestParam(required = false) String updname, @RequestParam(required = false) String updlastname,
                             Map<String, Object> model) {
        if (id == null) {
            Iterable<Person> users = personRepository.findAll();
            model.put("users", users);
            return "main";
        } else {
            Person updPerson = null;
            try {
                updPerson = personRepository.findById(id).get();
                String message = updPerson.toString();
                if (updname != null && !updname.isEmpty()) {
                    updPerson.setName(updname);
                }
                if (updlastname != null && !updlastname.isEmpty()) {
                    updPerson.setLastname(updlastname);
                }
                message += " изменён на " + updPerson.toString();
                personRepository.save(updPerson);
                model.put("message", message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mainPage(model);
        }
    }

    @PostMapping("/delete")
    public String deleteData(@RequestParam(required = false) Integer delid, Map<String, Object> model) {
        if (delid != null) {
            try {
                Person delPerson = personRepository.findById(delid).get();
                String message = "Сотрудник " + delPerson.toString() + " удалён";
                personRepository.delete(delPerson);
                model.put("message", message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mainPage(model);
    }

    private String mainPage(Map<String, Object> model) {
        Iterable<Person> persons = personRepository.findAll();
        model.put("persons", persons);
        return "main";
    }
}