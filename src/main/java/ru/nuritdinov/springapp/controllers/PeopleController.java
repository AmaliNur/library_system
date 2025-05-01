package ru.nuritdinov.springapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nuritdinov.springapp.dao.BookDAO;
import ru.nuritdinov.springapp.dao.PersonDAO;
import ru.nuritdinov.springapp.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String getAllPeople(Model model) {
        model.addAttribute("people", personDAO.getAll());
        return "people/index";
    }

    @GetMapping("/{personId}")
    public String getPerson(@PathVariable("personId") Integer personId, Model model) {
        model.addAttribute("person", personDAO.get(personId));
        model.addAttribute("books", personDAO.getBooksByPersonId(personId));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{personId}/edit")
    public String edit(Model model, @PathVariable("personId") int personId) {
        model.addAttribute("person", personDAO.get(personId));
        return "people/edit";
    }

    @PatchMapping("/{personId}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("personId") int personId) {
        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(personId, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{personId}")
    public String delete(@PathVariable("personId") int personId) {
        personDAO.delete(personId);
        return "redirect:/people";
    }

}
