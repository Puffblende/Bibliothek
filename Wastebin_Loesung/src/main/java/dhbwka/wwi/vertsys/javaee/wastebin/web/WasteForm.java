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

import dhbwka.wwi.vertsys.javaee.wastebin.jpa.WasteType;
import java.util.ArrayList;
import java.util.List;

/**
 * Formularinhalte, exakt so, wie sie eingegeben wurden. Diese Klasse
 * spiegelt im Prinzip die Entity-Klasse Waste, kann aber auch ungültige
 * Werte speichern.
 */
public class WasteForm {

    // Fehlermeldungen
    public List<String> errors = new ArrayList<>();
    
    // Eingabefelder
    private String name = "";
    private String content = "";
    private String type = "";

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public List<String> getErrors() {
        return errors;
    }
    
    public void setErrors(List<String> errors) {
        this.errors = errors;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    //</editor-fold>

    /**
     * Eingegebene Werte prüfen
     */
    public void checkValues() {
        if (name == null || name.trim().isEmpty()) {
            name = "";
            this.errors.add("Gib erst einen Namen ein.");
        }
        if (content == null || content.trim().isEmpty()) {
            content = "";
            this.errors.add("Gib erst einen Inhalt ein.");
        }
        if (type == null || type.trim().isEmpty()) {
            type = "NONE";
        }
    }

    /**
     * Richtige Konstante für das Feld "type" ermitteln
     * @return Konstante
     */
    public WasteType getWasteType() {
        try {
            return WasteType.valueOf(type);
        } catch (IllegalArgumentException ex) {
            return WasteType.NONE;
        }
    }
    
}
