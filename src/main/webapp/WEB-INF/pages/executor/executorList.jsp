<%@ include file="/common/taglibs.jsp"%>



<head>
    <title><fmt:message key="executorList.title"/></title>
    <meta name="heading" content="<fmt:message key='executorList.heading'/>"/>
    <meta name="menu" content="ProjectMenu"/>
</head>

<br/>
<br/>

<c:set var="project"  value="${map.project}"/>
<h5>Associated Executors with the Project</h5>
<br/>

Project: <a href="/project/get?id=${project.id}">${project.name}</a><br/>
<br/>

<div id="results">

<c:forEach var="executor" items="${map.executorList}">
<c:set var="executor" value="${executor}"/>

<div id="result">
<a href="/executor/get?ec_id=${executor.id}&project_id=${project.id}">${executor.name} </a><br/>
<i>Command Line:</i> <br/> <pre> ${executor.commandLine}</pre><br/>
<i>Working Directory</i> ${exectutor.workingDirectory}<br/>
<i>Time Started:</i> ${executor.timeStarted} <br/>
<i>Time Polled:</i> ${executor.timeStarted} <br/>
<i>Time Ended:</i> ${executor.timeStarted} <br/>
<br/>
<i>Result File: </i> ${executor.resultFile}

<br/>
</div>
</c:forEach>
</div>
