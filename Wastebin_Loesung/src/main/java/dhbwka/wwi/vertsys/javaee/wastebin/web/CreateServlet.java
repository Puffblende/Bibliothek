/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.wastebin.web;

import dhbwka.wwi.vertsys.javaee.wastebin.ejb.WasteBean;
import dhbwka.wwi.vertsys.javaee.wastebin.jpa.WasteType;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Anlegen eines neuen Textschnippsels.
 */
@WebServlet(urlPatterns = {"/new/"})
public class CreateServlet extends HttpServlet {

    public static final String URL = "/new/";
    
    @EJB
    WasteBean wasteBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Objekt mit leeren Eingabewerten im Request Context ablegen,
        // damit es beim Erstaufruf nicht zum Absturz kommt
        HttpSession session = request.getSession();
        WasteForm form = (WasteForm) session.getAttribute("waste_form");
        
        if (form == null) {
            session.setAttribute("waste_form", new WasteForm());
        }
        
        // Anfrage an die JSP weiterleiten
        request.setAttribute("wasteTypes", WasteType.values());
        request.getRequestDispatcher("/WEB-INF/form.jsp").forward(request, response);

        // Fehlermeldungen und so weiter aus der Session löschen, damit sie
        // beim nächsten Aufruf verschwinden
        session.removeAttribute("waste_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        
        // Eingegebene Werte prüfen
        WasteForm form = new WasteForm();
        
        form.setName(request.getParameter("name"));
        form.setContent(request.getParameter("content"));
        form.setType(request.getParameter("type"));

        form.checkValues();
        
        // Formular erneut anzeigen, wenn es Fehler gibt        
        if (!form.errors.isEmpty()) {
            // Formular erneut anzeigen, wenn es Fehler gibt
            HttpSession session = request.getSession();
            session.setAttribute("waste_form", form);

            response.sendRedirect(request.getContextPath() + CreateServlet.URL);
            return;
        }

        // Eintrag speichern und zurück zur Startseite
        this.wasteBean.createNewWaste(form.getName(), form.getContent(), form.getWasteType());
        response.sendRedirect(request.getContextPath() + IndexServlet.URL);
    }
    
}
