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

import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.Category;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.Task;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.TaskStatus;
import java.util.Date;

/**
 *
 * @author jka
 */
public class TaskDTO {
    private long id;
    private UserDTO owner;
    //private Category category;
    private String name;
    private String long_text;
    private String lend_to;
    private Date dueDate;
    private TaskStatus status;
    
    
    public TaskDTO(Task task){
        this.id = task.getId();
        this.owner = new UserDTO(task.getOwner());
        //this.category = new Category(task.getCategory());
        this.name = task.getShortText();
        this.long_text = task.getLongText();
        this.lend_to = task.getVerliehenAn();
        this.dueDate = task.getDueDate();
        this.status = task.getStatus();
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    
    
}
