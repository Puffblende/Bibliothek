/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.wastebin.ejb;

import dhbwka.wwi.vertsys.javaee.wastebin.jpa.WasteType;
import dhbwka.wwi.vertsys.javaee.wastebin.jpa.Waste;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Enterprise Java Bean zur Verwaltung der angelegten Textschnippsel.
 */
@Stateless
public class WasteBean {
    
    @PersistenceContext
    EntityManager em;
    
    /**
     * Anlegen eines neuen Textschnippsels.
     * @param name Bezeichnung
     * @param content Inhalt
     * @param type Syntaxhervorhebung
     * @return Der angelegte Textschnippsel
     */
    public Waste createNewWaste(String name, String content, WasteType type) {
        Waste waste = new Waste(name, content, type);
        em.persist(waste);
        return em.merge(waste);
    }
    
    /**
     * @return Liste mit allen Textschnippsel in umgedrehter Anlagereihenfolge
     */
    public List<Waste> findAllWaste() {
        return em.createQuery("SELECT w FROM Waste w ORDER BY w.id DESC").getResultList();
    }
    
    /**
     * Einzelnen Textschnippsel ermitteln
     * @param id ID des Textschnippsels
     * @return Gefundenes Objekt oder null
     */
    public Waste findWaste(long id) {
        return em.find(Waste.class, id);
    }
    
    /**
     * Löschen eines Textschnippsels. (Müll leeren :-))
     * 
     * @param id ID des zu löschenden Schnippsels
     * @return Der gelöschte Schnippsel oder null
     */
    public Waste deleteWaste(long id) {
        Waste waste = em.find(Waste.class, id);
        
        if (waste != null) {
            em.remove(waste);
        }
        
        return waste;
    }
    
    /**
     * Änderungen an einem Textschnippsel speichern.
     * @param waste Der zu ändernde Textschnippsel
     * @return Neue, gespeicherte Version des Textschnippsels
     */
    public Waste updateWaste(Waste waste) {
        return em.merge(waste);
    }
    
}
