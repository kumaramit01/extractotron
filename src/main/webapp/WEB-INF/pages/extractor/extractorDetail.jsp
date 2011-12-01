<%@ include file="/common/taglibs.jsp"%>



<head>
    <title><fmt:message key="extractor.title"/></title>
    <meta name="heading" content="<fmt:message key='extractorList.heading'/>"/>
    <meta name="menu" content="ExtractorMenu"/>
</head>

<div id="search">
<form method="get" action="${ctx}/extractor/search" id="searchForm">
    <input type="text" size="20" name="q" id="query" value="${param.q}"
           placeholder="Enter search terms"/>
    <input type="submit" value="<fmt:message key="button.search"/>"/>
</form>
</div>
<br/>
<br/>
<br/>
<br/>


<c:set var="extractor" value="${extractor}"/>
<br/>
<br/>
<div id="result">
<a href="/extractor/get?id=${extractor.id}">${extractor.name} </a> <br/>
<br/>
<i>Command Line:</i> 
<pre>
${extractor.commandLine}
</pre>
<br/>
<br/>
List of Supported Features: <br/>
<c:forEach var="feature" items="${supportedFeatures}">
${feature.label}
<br/>
</c:forEach>

</div>
<br/>
<br/>

<div>


</div>

