/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.dieBibliothek.books.ejb;

import dhbwka.wwi.vertsys.javaee.dieBibliothek.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.dieBibliothek.books.jpa.Genre;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Kategorien.
 */
@Stateless
@RolesAllowed("app-user")
public class GenreBean extends EntityBean<Genre, Long> {

    public GenreBean() {
        super(Genre.class);
    }

    /**
     * Auslesen aller Kategorien, alphabetisch sortiert.
     *
     * @return Liste mit allen Kategorien
     */
    public List<Genre> findAllSorted() {
        return this.em.createQuery("SELECT c FROM Genre c ORDER BY c.name").getResultList();
    }
}
