/* 
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
"use strict";

/**
 * Von der Klasse SongResource des Servers abgeleitete Klasse, die im Prinzip
 * dieselben Methoden besitzt. Hier rufen wir jedoch den REST-Webservice des
 * Servers auf, anstelle direkt auf eine Datenbank zuzugreifen.
 */
class UserResource {

    /**
     * Konstruktor.
     * @param {String} url Basis-URL des REST-Webservices (optional)
     */
    constructor(url) {
        this.url = url || "https://localhost:8443/DieBibliothek/webresources/api/user";
        this.username = "";
        this.password = "";
    }

    /**
     * Benutzername und Passwort für die Authentifizierung merken.
     * @param {String} username Benutzername
     * @param {String} password Passwort
     */
    setAuthData(username, password) {
        this.username = username;
        this.password = password;
    }


    async findAllUser() {
        let url = this.url;
        let response = await fetch(url, {
            headers: {
                "accept": "application/json",
            }
        });

        return await response.json();
    }
}