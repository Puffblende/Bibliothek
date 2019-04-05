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

import dhbwka.wwi.vertsys.javaee.dieBibliothek.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.books.ejb.GenreBean;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.books.ejb.BookBean;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.books.jpa.Book;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.books.jpa.BookStatus;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anlegen oder Bearbeiten einer Aufgabe.
 */
@WebServlet(urlPatterns = "/app/books/book/*")
public class BookEditServlet extends HttpServlet {

    @EJB
    BookBean bookBean;

    @EJB
    GenreBean genreBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.genreBean.findAllSorted());
        request.setAttribute("statuses", BookStatus.values());

        // Zu bearbeitende Aufgabe einlesen
        HttpSession session = request.getSession();

        Book book = this.getRequestedBook(request);
        request.setAttribute("edit", book.getId() != 0);
                                
        if (session.getAttribute("book_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("book_form", this.createBookForm(book));
        }

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/books/book_edit.jsp").forward(request, response);
        
        session.removeAttribute("book_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.saveBook(request, response);
                break;
            case "delete":
                this.deleteBook(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Aufgabe speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String bookGenre = request.getParameter("book_genre");
        String bookDueDate = request.getParameter("book_due_date");
        String bookDueTime = request.getParameter("book_due_time");
        String bookStatus = request.getParameter("book_status");
        String bookShortText = request.getParameter("book_short_text");
        String bookLongText = request.getParameter("book_long_text");
        String book_verliehenAn = request.getParameter("verliehen_an");

        Book book = this.getRequestedBook(request);

        if (bookGenre != null && !bookGenre.trim().isEmpty()) {
            try {
                book.setGenre(this.genreBean.findById(Long.parseLong(bookGenre)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }

        Date dueDate = WebUtils.parseDate(bookDueDate);
        Time dueTime = WebUtils.parseTime(bookDueTime);

        if (dueDate != null) {
            book.setDueDate(dueDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (dueTime != null) {
            book.setDueTime(dueTime);
        } else {
            errors.add("Die Uhrzeit muss dem Format hh:mm:ss entsprechen.");
        }

        try {
            book.setStatus(BookStatus.valueOf(bookStatus));
        } catch (IllegalArgumentException ex) {
            errors.add("Der ausgewählte Status ist nicht vorhanden.");
        }

        book.setShortText(bookShortText);
        book.setLongText(bookLongText);
        
        book.setVerliehenAn(book_verliehenAn);

        this.validationBean.validate(book, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.bookBean.update(book);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/books/list/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("book_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Aufgerufen in doPost: Vorhandene Aufgabe löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        Book book = this.getRequestedBook(request);
        this.bookBean.delete(book);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/books/list/"));
    }

    /**
     * Zu bearbeitende Aufgabe aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Book getRequestedBook(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Book book = new Book();
        book.setOwner(this.userBean.getCurrentUser());
        book.setDueDate(new Date(System.currentTimeMillis()));
        book.setDueTime(new Time(System.currentTimeMillis()));

        // ID aus der URL herausschneiden
        String bookId = request.getPathInfo();

        if (bookId == null) {
            bookId = "";
        }

        bookId = bookId.substring(1);

        if (bookId.endsWith("/")) {
            bookId = bookId.substring(0, bookId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            book = this.bookBean.findById(Long.parseLong(bookId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return book;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param book Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createBookForm(Book book) {
        Map<String, String[]> values = new HashMap<>();

        values.put("book_owner", new String[]{
            book.getOwner().getUsername()
        });

        if (book.getGenre() != null) {
            values.put("book_genre", new String[]{
                "" + book.getGenre().getId()
            });
        }

        values.put("book_due_date", new String[]{
            WebUtils.formatDate(book.getDueDate())
        });

        values.put("book_due_time", new String[]{
            WebUtils.formatTime(book.getDueTime())
        });

        values.put("book_status", new String[]{
            book.getStatus().toString()
        });

        values.put("book_short_text", new String[]{
            book.getShortText()
        });

        values.put("book_long_text", new String[]{
            book.getLongText()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}
