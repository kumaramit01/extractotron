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
<i>Name:</i> <a href="/project/get?id=${project.id}">${project.name} </a> <br/>
<br/>
<i>Description:</i> ${project.description}
<br/>
<br/>
<br/>
<h5>List of Extractors</h5>
<c:forEach var="extractor" items="${project.extractors}">
${extractor.name}
(<a href="/project/execute?id=${extractor.id}">Click To Execute</a>) 
<br/>
</c:forEach>
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
<h5>List of Executions</h5>
<c:forEach var="executor" items="${project.executionContexts}">
<a href="/project/executionDetail?id=${executor.uuid}&project_id=${project.id}">${executor.name}</a> 
<pre>
${executor.commandLine}
</pre>
<br/>
<br/>
</c:forEach>



</div>
<br/>
<br/>

<div>


</div>

