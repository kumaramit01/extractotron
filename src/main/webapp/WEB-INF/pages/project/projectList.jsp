<%@ include file="/common/taglibs.jsp"%>



<head>
    <title><fmt:message key="projectList.title"/></title>
    <meta name="heading" content="<fmt:message key='projectList.heading'/>"/>
    <meta name="menu" content="ProjectMenu"/>
</head>

<div id="search">
<form method="get" action="${ctx}/project/search" id="searchForm">
    <input type="text" size="20" name="q" id="query" value="${param.q}"
           placeholder="Enter search terms"/>
    <input type="submit" value="<fmt:message key="button.search"/>"/>
</form>
</div>
<br/>
<br/>
<br/>
<br/>


<div id="results">
<c:forEach var="project" items="${projectList}">
<c:set var="project" value="${project}"/>

<div id="result">
<i>Name:</i> <a href="/project/get?id=${project.id}">${project.name} </a><br/>
<i>Description:</i> ${project.description}
<br/>
<br/>
<a href="/projectform?projectId=${project.id}">Edit</a>|<a href="/projectform?projectId=${project.id}">Delete</a>
</div>
<br/>
</c:forEach>
</div>
<%!


%>