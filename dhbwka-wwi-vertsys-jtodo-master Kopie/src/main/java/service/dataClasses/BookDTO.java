/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package service.dataClasses;

import dhbwka.wwi.vertsys.javaee.dieBibliothek.books.jpa.Book;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.books.jpa.BookStatus;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.books.jpa.Genre;
import java.text.Format;
import java.text.SimpleDateFormat;


/**
 *
 * @author jka
 */
public class BookDTO {
    
    Format simpleDate = new SimpleDateFormat("dd.MM.yyyy");

    private long id;
    private UserDTO owner;
    private Genre genre;
    private String name;
    private String long_text;
    private String lend_to;
    private String due_date;
    private StatusDTO status;

    public BookDTO(Book book) {
        this.id = book.getId();
        this.due_date = simpleDate.format(book.getDueDate());
        this.owner = new UserDTO(book.getOwner());
        this.genre = book.getGenre();
        this.name = book.getShortText();
        this.long_text = book.getLongText();
        this.lend_to = book.getVerliehenAn();
        this.status = new StatusDTO(book.getStatus());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLong_text() {
        return long_text;
    }

    public void setLong_text(String long_text) {
        this.long_text = long_text;
    }

    public String getLend_to() {
        return lend_to;
    }

    public void setLend_to(String lend_to) {
        this.lend_to = lend_to;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }


    
    
    
    

}
