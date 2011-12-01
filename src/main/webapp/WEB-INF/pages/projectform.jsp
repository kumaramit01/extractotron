<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="heading" content="<fmt:message key='projectProfile.heading'/>"/>
    <meta name="menu" content="ProjectMenu"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head>

<spring:bind path="project.*">
    <c:if test="${not empty status.errorMessages}">
    <div class="error">
        <c:forEach var="error" items="${status.errorMessages}">
            <img src="<c:url value="/images/iconWarning.gif"/>"
                alt="<fmt:message key="icon.warning"/>" class="icon"/>
            <c:out value="${error}" escapeXml="false"/><br />
        </c:forEach>
    </div>
    </c:if>
</spring:bind>

<form:form commandName="project" method="post" action="projectform" onsubmit="return onFormSubmit(this)" id="projectForm">
<form:hidden path="id"/>

<input type="hidden" name="from" value="<c:out value="${param.from}"/>"/>

<ol>
  
      
  
  
  
    <li class="buttonBar right">
        <input type="submit" class="button" name="save" onclick="bCancel=false" value="<fmt:message key="button.save"/>"/>

        <c:if test="${param.method != 'Add'}">
            <input type="submit" class="button" name="delete" onclick="bCancel=true;return confirmDelete('project')"
                value="<fmt:message key="button.delete"/>"/>
        </c:if>

        <input type="submit" class="button" name="cancel" onclick="bCancel=true" value="<fmt:message key="button.cancel"/>"/>
    </li>
    <li class="info">
        <c:choose>
            <c:when test="${param.from == 'list'}">
                <p><fmt:message key="projectProfile.admin.message"/></p>
            </c:when>
            <c:otherwise>
                <p><fmt:message key="projectProfile.message"/></p>
            </c:otherwise>
        </c:choose>
    </li>
    

    
    
    <li>
    <div class="left">
        <appfuse:label styleClass="desc" key="project.name"/>
        <form:errors path="name" cssClass="fieldError"/>
        <form:input path="name" id="name" cssClass="text large" cssErrorClass="text large error"/>
    </div>
    </li>
    
    <li>
            <div class="left">
                <appfuse:label styleClass="desc" key="project.description"/>
                <form:errors path="description" cssClass="fieldError"/>
                <form:textarea path="description" id="description" cssClass="text medium" cssErrorClass="text medium error" />
            </div>
    
    </li>
 
    <li>
    <div class="left">
        <appfuse:label styleClass="desc" key="project.commandLine"/>
        <form:errors path="commandLine" cssClass="fieldError"/>
        <form:input path="commandLine" id="commandLine" cssClass="text large" cssErrorClass="text large error"/>
    </div>
    </li>
    <li>
        <div class="left">
            <appfuse:label styleClass="desc" key="project.status"/>
            <form:errors path="status" cssClass="fieldError"/>
            <form:input path="status" id="status" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
        </div>
   
    </li>
    
      <li>
        <fieldset class="pickList">
            <legend><fmt:message key="projectProfile.assignCollections"/></legend>
            <table class="pickList">
                <tr>
                    <th class="pickLabel">
                        <appfuse:label key="project.availableCollections" colon="false" styleClass="required"/>
                    </th>
                    <td></td>
                    <th class="pickLabel">
                        <appfuse:label key="project.songCollections" colon="false" styleClass="required"/>
                    </th>
                </tr>
                <c:set var="leftList" value="${availableCollections}" scope="request"/>
                <c:set var="rightList" value="${project.songCollectionList}" scope="request"/>
                <c:import url="/WEB-INF/pages/pickList.jsp">
                    <c:param name="listCount" value="1"/>
                    <c:param name="leftId" value="availableCollections"/>
                    <c:param name="rightId" value="songCollections1"/>
                </c:import>
            </table>
        </fieldset>
    </li>
    
     <li>
        <fieldset class="pickList">
            <legend><fmt:message key="projectProfile.assignExtractor"/></legend>
            <table class="pickList">
                <tr>
                    <th class="pickLabel">
                        <appfuse:label key="project.availableExtractors" colon="false" styleClass="required"/>
                    </th>
                    <td></td>
                    <th class="pickLabel">
                        <appfuse:label key="project.extractors" colon="false" styleClass="required"/>
                    </th>
                </tr>
                <c:set var="leftList" value="${availableExtractors}" scope="request"/>
                <c:set var="rightList" value="${project.extractorsList}" scope="request"/>
                <c:import url="/WEB-INF/pages/pickOne.jsp">
                    <c:param name="listCount" value="1"/>
                    <c:param name="leftId" value="availableExtractors"/>
                    <c:param name="rightId" value="selectedExtractor"/>
                </c:import>
            </table>
        </fieldset>
    </li>
    


    <li>
     <div class="left">
      <input type="submit" class="button" name="save" onclick="bCancel=false" value="<fmt:message key="button.save"/>"/>

      <c:if test="${param.method != 'Add'}">
          <input type="submit" class="button" name="delete" onclick="bCancel=true;return confirmDelete('project')"
              value="<fmt:message key="button.delete"/>"/>
      </c:if>

      <input type="submit" class="button" name="cancel" onclick="bCancel=true" value="<fmt:message key="button.cancel"/>"/>
    </div>
    </li>
</ol>
</form:form>

<script type="text/javascript">
    Form.focusFirstElement($('projectForm'));
    highlightFormElements();



<!-- This is here so we can exclude the selectAll call when roles is hidden -->
function onFormSubmit(theForm) {
	  selectAll('songCollections1');
	  selectAll('selectedExtractor');
	return true;//validateProject(theForm);
}
</script>

<v:javascript formName="project" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>

