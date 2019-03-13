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
import dhbwka.wwi.vertsys.javaee.wastebin.jpa.Waste;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Detailanzeige eines vorhandenen Textschnippsels
 */
@WebServlet(urlPatterns={"/view/*"})
public class ViewServlet extends HttpServlet {
    
    @EJB
    WasteBean wasteBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Angeforderten Datensatz ermitteln
        // Hinweis: request.getPathInfo() liefert null, "/" oder "/123/"
        long id = -1;
        String pathInfo = request.getPathInfo();
        
        if (pathInfo != null && pathInfo.length() > 2) {
            try {
                id = Long.parseLong(pathInfo.split("/")[1]);
            } catch (NumberFormatException ex) {
                // URL enthält keine gültige Long-Zahl
            }
        }
        
        Waste waste = wasteBean.findWaste(id);
        
        // Zurück zur Startseite, wenn der Satz nicht gefunden wirde
        if (waste == null) {
            response.sendRedirect(request.getContextPath() + IndexServlet.URL);
            return;
        }
        
        // Anfrage an die JSP weiterreichen
        request.setAttribute("waste", waste);
        request.getRequestDispatcher("/WEB-INF/view.jsp").forward(request, response);
    }
}
