"use-strict";

class BookResource {
 
 
 /**
     * Konstruktor.
     * @param {String} url Basis-URL des REST-Webservices (optional)
     */
    constructor(url) {
        this.url = url || "https://localhost:8443/DieBibliothek/webresources/api/book";
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


    async findAllBoooks() {
        let url = this.url;
        let response = await fetch(url, {
            headers: {
                "accept": "application/json"
            }
        });

        return await response.json();
    }
}