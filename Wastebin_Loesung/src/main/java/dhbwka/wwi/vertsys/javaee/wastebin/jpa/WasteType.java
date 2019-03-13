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

/**
 * Aufzählung der verfügbaren Sprachen für das Syntax Highlighting,
 */
public enum WasteType {

    NONE, JAVA, C, PYTHON, JAVASCRIPT, RUBY, BASH, PHP, HTML, CSS, JSON, XML, YAML, PERL;

    /**
     * Bezeichnung ermitteln
     *
     * @return Bezeichnung
     */
    public String getLabel() {
        switch (this) {
            case NONE:
                return "Nur Text";
            case JAVA:
                return "Java";
            case C:
                return "C/C++";
            case PYTHON:
                return "Python";
            case JAVASCRIPT:
                return "JavaScript";
            case RUBY:
                return "Ruby";
            case BASH:
                return "Bash";
            case PERL:
                return "Perl";
            default:
                return this.toString();
        }
    }

    /**
     * Name der Programmiersprache für den Prism.js Syntaxhighlighter ermitteln.
     * @return Name der Sprache für prism.js
     */
    public String getPrism() {
        switch (this) {
            case JAVA:
                return "language-java";
            case C:
                return "language-clike";
            case PYTHON:
                return "language-python";
            case JAVASCRIPT:
                return "language-javascript";
            case RUBY:
                return "language-ruby";
            case BASH:
                return "language-bash";
            case PHP:
                return "language-php";
            case HTML:
                return "language-markup";
            case CSS:
                return "language-css";
            case JSON:
                return "language-json";
            case XML:
                return "language-markup";
            case YAML:
                return "language-yaml";
            case PERL:
                return "language-perl";
            default:
                return "";
        }
    }
}
