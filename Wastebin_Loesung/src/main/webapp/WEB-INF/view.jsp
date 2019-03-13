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
        <c:out value="${waste.name}"/>
    </jsp:attribute>

    <jsp:attribute name="head">
        <!-- Syntaxhighlighting: http://prismjs.com/ -->
        <link rel="stylesheet" href="<c:url value="/prism/prism.css"/>" />
    </jsp:attribute>
        
    <jsp:attribute name="main">
        <h1>🗑️ <c:out value="${waste.name}"/></h1>
        
        <div class="margin-bottom">
            Typ: ${waste.type.label}
        </div>
        
        <div class="margin-bottom">
            <a href="<c:url value="/index.html"/>">🏚️ Zurück zur Übersicht</a>
        </div>

        <pre><code class="${waste.type.prism}"><c:out value="${waste.content}"/></code></pre>

        <script src="<c:url value="/prism/prism.js"/>"></script>
    </jsp:attribute>
</template:base>