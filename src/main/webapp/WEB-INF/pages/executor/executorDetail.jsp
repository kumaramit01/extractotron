<%@ include file="/common/taglibs.jsp"%>



<head>
    <title><fmt:message key="executor.title"/></title>
    <meta name="heading" content="<fmt:message key='executorList.heading'/>"/>
    <meta name="menu" content="ProjectMenu"/>
</head>


<c:set var="project"  value="${map.project}"/>

<c:set var="executor" value="${map.executor}"/>
<br/>
<br/>
<div id="result">
Project: <a href="/project/get?id=${project.id}">${project.name}</a><br/>
<a href="/executor/get?id=${executor.id}">${executor.name} </a> <br/>
<br/>
<i>Command Line:</i> 
<pre>
${executor.commandLine}
</pre>
<br/>

</div>

