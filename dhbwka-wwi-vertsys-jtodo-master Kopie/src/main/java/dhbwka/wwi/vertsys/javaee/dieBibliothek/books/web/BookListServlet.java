/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.dieBibliothek.books.web;

import dhbwka.wwi.vertsys.javaee.dieBibliothek.books.ejb.GenreBean;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.books.ejb.BookBean;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.books.jpa.Genre;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.books.jpa.Book;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.books.jpa.BookStatus;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die tabellarische Auflisten der Aufgaben.
 */
@WebServlet(urlPatterns = {"/app/books/list/"})
public class BookListServlet extends HttpServlet {

    @EJB
    private GenreBean genreBean;
    
    @EJB
    private BookBean bookBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.genreBean.findAllSorted());
        request.setAttribute("statuses", BookStatus.values());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchGenre = request.getParameter("search_genre");
        String searchStatus = request.getParameter("search_status");

        // Anzuzeigende Aufgaben suchen
        Genre genre = null;
        BookStatus status = null;

        if (searchGenre != null) {
            try {
                genre = this.genreBean.findById(Long.parseLong(searchGenre));
            } catch (NumberFormatException ex) {
                genre = null;
            }
        }

        if (searchStatus != null) {
            try {
                status = BookStatus.valueOf(searchStatus);
            } catch (IllegalArgumentException ex) {
                status = null;
            }

        }

        List<Book> books = this.bookBean.search(searchText, genre, status);
        request.setAttribute("books", books);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/books/book_list.jsp").forward(request, response);
    }
}
