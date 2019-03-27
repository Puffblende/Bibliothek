<%-- 
    Document   : newjspchange_userdata
    Created on : 26.03.2019, 15:48:37
    Author     : t.maechtel
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri ="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name ="title">
        Einstellungen    
    </jsp:attribute>
        
    <jsp:attribute name ="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>
        
    <jsp:attribute name="menu"> 
        
       
        <div class="menuitem" >
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>
    </jsp:attribute>  
        
    <jsp:attribute name="content">
        as
    </jsp:attribute>    
        
</template:base>