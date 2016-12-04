<%@ page import="jetbrains.buildServer.web.util.SessionUser" %>
<%@ include file="/include.jsp"%>
<%@ include file="constants.jsp"%>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags"%>

<jsp:useBean id="providerType" scope="request" type="de.dero.teamcity.gitlab.GitLabIssueProviderType"/>

<div>
  <table class="editProviderTable">
    <tr>
      <th><label for="${name}" class="shortLabel">Display Name: <l:star/></label></th>
      <td>
        <props:textProperty name="${name}" maxlength="100"/>
        <span id="error_${name}" class="error"/>
      </td>
    </tr>
    <tr>
      <th><label for="${project}" class="shortLabel">Project URL: <l:star/></label></th>
      <td>
        <props:textProperty name="${project}" maxlength="100"/>
        <span id="error_${project}" class="error"/>
      </td>
    </tr>
    <tr>
      <th><label for="${pattern}" class="shortLabel">Pattern: <l:star/></label></th>
      <td>
        <props:textProperty name="${pattern}" maxlength="100"/>
          <span class="fieldExplanation">Use the regex syntax, e.g. #(\d+)<bs:help file="Integrating+TeamCity+with+Issue+Tracker"/></span>
          <span id="error_${pattern}" class="error" />
      </td>
    </tr>
    <tr>
      <th><label for="${token}" class="shortLabel">Api Token: <l:star/></label></th>
      <td>
        <props:textProperty name="${token}" maxlength="20"/>
        <span id="error_${token}" class="error"/>
      </td>
    </tr>
    <tr>
      <th><label for="${tokenType}" class="shortLabel">Token Type:</label></th>
      <td>
        <props:selectProperty name="${tokenType}">
          <props:option value="${tokenTypeAccess}">Access</props:option>
          <props:option value="${tokenTypePrivate}" selected="true">Private</props:option>
        </props:selectProperty>
        <span id="error_${tokenType}" class="error"/>
      </td>
    </tr>
    <tr>
      <th><label for="${authType}" class="shortLabel">Auth Type:</label></th>
      <td>
        <props:selectProperty name="${authType}">
          <props:option value="${authTypeHeader}">Header</props:option>
          <props:option value="${authTypeUrl}">Url Parameter</props:option>
        </props:selectProperty>
        <span id="error_${authType}" class="error"/>
      </td>
    </tr>
  </table>
</div>
