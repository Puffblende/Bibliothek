/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.dieBibliothek.common.web;

import dhbwka.wwi.vertsys.javaee.dieBibliothek.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.common.jpa.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author t.maechtel
 */
@WebServlet(name = "SettingsServlet", urlPatterns = {"/app/settings/"})
public class SettingsServlet extends HttpServlet {
    
    @EJB    
    ValidationBean validationBean;
    
    @EJB
    UserBean userBean;

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getSession().setAttribute("User", this.userBean.getCurrentUser());
        
        request.getRequestDispatcher("/WEB-INF/login/change_userdata.jsp").forward(request, response);        
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        User user = this.userBean.getCurrentUser();
        
        String first_name = request.getParameter("change_first_name");
        String last_name = request.getParameter("change_last_name");
        String password1 = request.getParameter("change_password1");
        String password2 = request.getParameter("change_password2");
        
        List<String> errors = this.validationBean.validate(user);
        
        if (errors.isEmpty()) {
            if (first_name.isEmpty()) {
                
            } else {
                this.userBean.changeFirstName(user, first_name);
            }
            if(last_name.isEmpty()){
                
            }else{
                this.userBean.changeLastName(user, last_name);
            }
            if(password2.isEmpty()){
                
            }else if (password1.isEmpty()){
                errors.add("Bitte tragen Sie das alte Passwort ein!");
            }else{
                try {
                    this.userBean.changePassword(user, password1, password2);
                } catch (UserBean.InvalidCredentialsException ex) {
                    errors.add(ex.getMessage());
                }
            }
            
            
        }
        
        if (errors.isEmpty()){
            response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));
        }else{
            response.sendRedirect(request.getRequestURI());
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
