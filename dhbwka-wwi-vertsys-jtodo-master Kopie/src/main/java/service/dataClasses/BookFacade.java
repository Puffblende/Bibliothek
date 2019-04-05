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

import dhbwka.wwi.vertsys.javaee.dieBibliothek.books.ejb.BookBean;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.books.jpa.Book;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author jka
 */

@Stateless
public class BookFacade {
    
    @EJB
    BookBean bookBean;
    
    public List<BookDTO> findAllBooks(){
        List<Book> books = bookBean.findAll();
        return books.stream().map((book) ->{
            BookDTO bookDTO = new BookDTO(book);
            return bookDTO;
        }).collect(Collectors.toList());
    }
    
    public List<BookDTO> findAllBookName(String name){
        List<Book> result_books = bookBean.findByName(name);
        return result_books.stream().map((book)->
        {
            BookDTO bookDTO = new BookDTO(book);
            return bookDTO;
        }).collect(Collectors.toList());
    }
    
}
