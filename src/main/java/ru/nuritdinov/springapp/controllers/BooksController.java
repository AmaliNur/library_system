package ru.nuritdinov.springapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nuritdinov.springapp.dao.BookDAO;
import ru.nuritdinov.springapp.dao.PersonDAO;
import ru.nuritdinov.springapp.models.Book;
import ru.nuritdinov.springapp.models.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller()
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookDAO.getAll());
        return "books/index";
    }

    @GetMapping("/{bookId}")
    public String getBook(@PathVariable("bookId") int bookId, Model model,
                          @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.get(bookId));

        Optional<Person> bookOwner = bookDAO.getBookOwner(bookId);
        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDAO.getAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{bookId}/edit")
    public String edit(Model model, @PathVariable("bookId") int bookId) {
        model.addAttribute("book", bookDAO.get(bookId));
        return "books/edit";
    }

    @PatchMapping("/{bookId}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("bookId") int bookId) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(bookId, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{bookId}")
    public String delete(@PathVariable("bookId") int bookId) {
        bookDAO.delete(bookId);
        return "redirect:/books";
    }

    @PatchMapping("/{bookId}/release")
    public String releaseBook(@PathVariable("bookId") int bookId) {
        bookDAO.releaseBook(bookId);
        return "redirect:/books";
    }

    @PatchMapping("/{bookId}/assign")
    public String assignBook(@PathVariable("bookId") int bookId, @ModelAttribute("person") Person person) {
        bookDAO.assignBook(bookId, person);
        return "redirect:/books";
    }

}
