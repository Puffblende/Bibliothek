/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.wastebin.jpa;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.TableGenerator;

/**
 * Ein Müll bzw. ein Textschnippsel, den ein Anwender angelegt hat.
 */
@Entity
public class Waste implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "waste_ids")
    @TableGenerator(name = "waste_ids", initialValue = 0, allocationSize = 50)
    private Long id;

    private String name = "";
    private WasteType type = WasteType.NONE;

    @Lob
    private String content = "";

    //<editor-fold defaultstate="collapsed" desc="Konstrukturen">
    public Waste() {
    }
    
    public Waste(String name, String content, WasteType type) {
        this.name = name;
        this.content = content;
        this.type = type;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public WasteType getType() {
        return type;
    }

    public void setType(WasteType type) {
        this.type = type;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Von Objekt geerbter Kram">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Waste)) {
            return false;
        }
        Waste other = (Waste) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dhbwka.wwi.vertsys.javee.wastebin.WasteEntity[ id=" + id + " ]";
    }
    //</editor-fold>

}
