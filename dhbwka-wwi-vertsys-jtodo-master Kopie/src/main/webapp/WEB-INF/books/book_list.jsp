<%-- 
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Liste der Bücher
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/books/book/new/"/>">Neues Buch anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/books/categories/"/>">Neues Genre anlegen</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Beschreibung"/>

            <select name="search_genre">
                <option value="">Alle Kategorien</option>

                <c:forEach items="${categories}" var="genre">
                    <option value="${genre.id}" ${param.search_genre == genre.id ? 'selected' : ''}>
                        <c:out value="${genre.name}" />
                    </option>
                </c:forEach>
            </select>

            <select name="search_status">
                <option value="">Alle Stati</option>

                <c:forEach items="${statuses}" var="status">
                    <option value="${status}" ${param.search_status == status ? 'selected' : ''}>
                        <c:out value="${status.label}"/>
                    </option>
                </c:forEach>
            </select>

            <button class="icon-search" type="submit">
                Suchen
            </button>
        </form>

        <%-- Gefundene Aufgaben --%>
        <c:choose>
            <c:when test="${empty books}">
                <p>
                    Es wurden keine Aufgaben gefunden. 🐈
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.dieBibliothek.common.web.WebUtils"/>
                
                <table>
                    <thead>
                        <tr>
                            <th>Bezeichnung</th>
                            <th>Kategorie</th>
                            <th>Eigentümer</th>
                            <th>Status</th>
                            <th>Verfügbar seit</th>
                            <th>Verliehen an</th>
                        </tr>
                    </thead>
                    <c:forEach items="${books}" var="book">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/books/book/${book.id}/"/>">
                                    <c:out value="${book.shortText}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${book.genre.name}"/>
                            </td>
                            <td>
                                <c:out value="${book.owner.username}"/>
                            </td>
                            <td>
                                <c:out value="${book.status.label}"/>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(book.dueDate)}"/>
                                <!--<c:out value="${utils.formatTime(book.dueTime)}"/>-->
                            </td>
                            <td>
                                <c:out value="${book.verliehenAn}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>