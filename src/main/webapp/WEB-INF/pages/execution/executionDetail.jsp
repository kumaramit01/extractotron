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

<a href="/log/view?id=${ec.uuid}&type=result">Result File</a><br/>
<a href="/log/view?id=${ec.uuid}&type=error">Error Console</a><br/>
<a href="/log/view?id=${ec.uuid}&type=input">Input File</a><br/>
<a href="/log/view?id=${ec.uuid}&type=output">Console outputs</a><br/>
<a href="/log/view?id=${ec.uuid}&type=commandline">Command Line</a><br/>
<a href="/log/download?id=${ec.uuid}">Download Results</a><br/>
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
<h5>List of Executors</h5>
<ol>
<c:forEach var="executor" items="${project.executionContexts}">
<li>
${executor.name} (${executor.status})<br/>
<a href="/log/view?id=${executor.id}&type=result">Result File</a><br/>
<a href="/log/view?id=${executor.id}&type=error">Error Console</a><br/>
<a href="/log/view?id=${executor.id}&type=input">Input File</a><br/>
<a href="/log/view?id=${executor.id}&type=output">Console outputs</a><br/>
<a href="/log/view?id=${executor.id}&type=commandline">Command Line</a><br/>
<a href="/log/download?id=${executor.id}">Download Results</a><br/>
<br/>


<a href="/executor/get?project_id=${project.id}&ec_id=${executor.id}">View</a> |
<a href="/project/remove_executor?project_id=${project.id}&ec_id=${executor.id}">Remove</a> |
<a href="/executor/execute?project_id=${project.id}&ec_id=${executor.id}">Execute</a> |
<br/>
</li>
</c:forEach>
</ol>


</div>
<br/>
<br/>

<div>


</div>

