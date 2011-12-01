<%@ include file="/common/taglibs.jsp"%>



<head>
    <title><fmt:message key="extractorList.title"/></title>
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

<h5>Available Features</h5>
<br/>
 <select name="featureList" multiple="multiple" id="featureList" size="7">
    <c:if test="${supportedFeatures != null}">
        <c:forEach var="feature" items="${supportedFeatures}" varStatus="status">
            <option value="<c:out value="${feature.value}"/>">
                <c:out value="${feature.label}" escapeXml="false" />
            </option>
        </c:forEach>
    </c:if>
 </select>

<br/>
<br/>
<br/>

<h5>Available Extractors</h5>

<div id="results">
<c:forEach var="extractor" items="${extractorList}">
<c:set var="extractor" value="${extractor}"/>

<div id="result">
<a href="/extractor/get?id=${extractor.id}">${extractor.name} </a><br/>
<i>Command Line:</i> <br/> <pre> ${extractor.commandLine}</pre>
<br/>
</div>
</c:forEach>
</div>
