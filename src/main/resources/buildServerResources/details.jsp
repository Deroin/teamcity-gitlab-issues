<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/include.jsp"%>
<jsp:useBean id="issue" scope="request" type="jetbrains.buildServer.issueTracker.IssueEx"/>
<c:set var="stateClass" value="hidden"/>
<c:set var="issueData" value="${issue.issueDataOrNull}"/>
<bs:issueDetailsPopup issue="${issue}" stateClass="${stateClass}">
    <jsp:attribute name="otherFields">
      <c:if test="${not empty issueData.milestone}">
        <td title="Milestone" class="milestone">${issueData.milestone}</td>
      </c:if>
      <c:if test="${not empty issueData.description}">
            </tr>
          </table>
          <table class="other">
            <tr>
                <td title="Description" class="description">${issueData.description}</td>
      </c:if>
    </jsp:attribute>
</bs:issueDetailsPopup>
