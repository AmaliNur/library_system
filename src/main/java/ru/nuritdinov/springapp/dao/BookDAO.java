package ru.nuritdinov.springapp.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nuritdinov.springapp.models.Book;
import ru.nuritdinov.springapp.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // получить все книги
    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    // получить книгу
    public Book get(int bookId) {
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?", new Object[]{bookId},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    // сохранить книгу
    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(book_title, book_author,year_of_book) VALUES (?, ?, ?)", book.getBookTitle(),
                book.getBookAuthor(), book.getYearOfBook());
    }

    // изменить книгу
    public void update(int bookId, Book updatedBook) {
        jdbcTemplate.update("UPDATE book SET book_title=?, book_author=?, year_of_book=? WHERE book_id=?",
                updatedBook.getBookTitle(), updatedBook.getBookAuthor(), updatedBook.getYearOfBook() ,bookId);
    }

    // удалить книгу
    public void delete(int bookId) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", bookId);
    }

    // получить владельца книги
    public Optional<Person> getBookOwner(int personId) {
        // Выбираем все колонки таблицы Person из объединенной таблицы
        return jdbcTemplate.query("SELECT person.* FROM book JOIN person ON book.person_id = person.person_id " +
                        "WHERE book.book_id = ?", new Object[]{personId}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    // освободить книгу
    public void releaseBook(int bookId) {
        jdbcTemplate.update("UPDATE book SET person_id = NULL WHERE book_id=?", bookId);
    }

    // назначить книгу
    public void assignBook(int bookId, Person selectedPerson) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE book_id = ?", selectedPerson.getPersonId(), bookId);
    }
}
