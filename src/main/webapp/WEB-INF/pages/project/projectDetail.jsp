<%@ include file="/common/taglibs.jsp"%>



<head>
    <title><fmt:message key="project.title"/></title>
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


<c:set var="project" value="${project}"/>
<a href="/projectform?projectId=${project.id}">Edit</a>|<a href="/projectform?projectId=${project.id}">Delete</a>
<br/>
<br/>
<div id="result">
<i>Name:</i> <a href="/project/get?id=${collection.id}">${project.name} </a> <br/>
<br/>
<i>Description:</i> ${project.description}
<br/>
<i>Status:</i> ${project.status}
<br/>
<i>Command Line:</i> ${project.commandLine}
<br/>
<i>Time Started:</i> ${project.timeStarted}

<br/>
<i>Time Ended:</i> ${project.timeEnded}



</div>
<br/>
<br/>

<div>


</div>

