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

import dhbwka.wwi.vertsys.javaee.jtodo.common.jpa.User;

/**
 *
 * @author jka
 */
public class UserDTO {
    
    private String username;
    private String first_name;
    private String last_name;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.first_name = user.getFirst_name();
        this.last_name = user.getLast_name();
    }
    
    
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    
    
    
}
