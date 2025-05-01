package ru.nuritdinov.springapp.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {

    private int bookId;
    private Integer personId;

    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название должно быть от 2 до 100 символов длиною")
    private String bookTitle;

    @NotEmpty(message = "Автор не должен быть пустым")
    @Size(min = 2, max = 100, message = "Автор должен быть от 2 до 100 символов длиною")
    private String bookAuthor;

    @Min(value = 1500, message = "Год должен быть больше, чем 1500")
    private int yearOfBook;

    public Book() {}

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getYearOfBook() {
        return yearOfBook;
    }

    public void setYearOfBook(int yearOfBook) {
        this.yearOfBook = yearOfBook;
    }
}
