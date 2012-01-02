<%@ include file="/common/taglibs.jsp"%>



<head>
    <title>Execution Details</title>
    <meta name="heading" content="<fmt:message key='execution.heading'/>"/>
    <meta name="menu" content="ProjectMenu"/>
</head>

<div id="search">
<form method="get" action="${ctx}/executor/search" id="searchForm">
    <input type="text" size="20" name="q" id="query" value="${param.q}"
           placeholder="Enter search terms"/>
    <input type="submit" value="<fmt:message key="button.search"/>"/>
</form>
</div>
<br/>
<br/>
<br/>
<br/>

<c:set var="project"  value="${map.project}"/>
<c:set var="ec" value="${map.executionContext}"/>

<br/>
<br/>
<h3>Execution Information</h3>
<div id="result1">
${ec.name} (${ec.status})<br/>
Time Created: ${ec.timeCreated}<br/>
Time Started: ${ec.timeStarted}<br/>
Time Finished: ${ec.timeEnded}<br/>
<a href="/log/project/${project.id}/id/${ec.uuid}">View Directory Listing</a>|
<a href="/log/project/${project.id}/id/${ec.uuid}/result">Result File</a><br/>
<a href="/log/project/${project.id}/id/${ec.uuid}/error">Error Console</a>|
<a href="/log/project/${project.id}/id/${ec.uuid}/input">Input File</a><br/>
<a href="/log/project/${project.id}/id/${ec.uuid}/output">Console outputs</a>|
<a href="/log/project/${project.id}/id/${ec.uuid}/commandline">Command Line</a><br/>
<br/>
</div>
<br/>
<h3>Project Information</h3>
<div id="result">
<i>Name:</i> <a href="/project/get?id=${project.id}">${project.name} </a> <br/>
<br/>
<i>Description:</i> ${project.description}
<br/>
<br/>
<br/>
<h5>List of Collections</h5>
<c:forEach var="collection" items="${project.songCollections}">
<a href="/collection/get?id=${collection.id}">${collection.name}</a>
<br/>
</c:forEach>
<br/>
<hr/>
</div>

