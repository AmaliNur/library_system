package ru.nuritdinov.springapp.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nuritdinov.springapp.models.Book;
import ru.nuritdinov.springapp.models.Person;

import java.util.List;
import java.util.Optional;


@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // получить всех людей
    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    // получить человека
    public Person get(int personId) {
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id=?", new Object[]{personId},
                        new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    // сохранить человека
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(full_name, year_of_birth) VALUES (?, ?)", person.getFullName(),
                person.getYearOfBirth());
    }

    // изменить человека
    public void update(int personId, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET full_name=?, year_of_birth=? WHERE person_id=?",
                updatedPerson.getFullName(), updatedPerson.getYearOfBirth(), personId);
    }

    // удалить человека
    public void delete(int personId) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id=?", personId);
    }

    public Optional<Person> getPersonByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM person WHERE full_name=?", new Object[]{fullName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }

}
