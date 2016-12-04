<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="de.dero.teamcity.gitlab.GitLabConstants" %>
<%@ page import="org.gitlab.api.AuthMethod" %>
<%@ page import="org.gitlab.api.TokenType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="name" value ="<%=GitLabConstants.NAME%>" />
<c:set var="project" value="<%=GitLabConstants.PROJECT_URL%>" />
<c:set var="pattern" value="<%=GitLabConstants.PATTERN%>" />
<c:set var="token" value ="<%=GitLabConstants.TOKEN%>" />
<c:set var="tokenType" value ="<%=GitLabConstants.TOKEN_TYPE%>" />
<c:set var="tokenTypeAccess" value ="<%=TokenType.ACCESS_TOKEN%>" />
<c:set var="tokenTypePrivate" value ="<%=TokenType.PRIVATE_TOKEN%>" />
<c:set var="authType" value ="<%=GitLabConstants.AUTH_TYPE%>" />
<c:set var="authTypeHeader" value ="<%=AuthMethod.HEADER%>" />
<c:set var="authTypeUrl" value ="<%=AuthMethod.URL_PARAMETER%>" />
