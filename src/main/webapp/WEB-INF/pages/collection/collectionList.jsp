<%@ include file="/common/taglibs.jsp"%>



<head>
    <title><fmt:message key="userList.title"/></title>
    <meta name="heading" content="<fmt:message key='collectionList.heading'/>"/>
    <meta name="menu" content="CollectionMenu"/>
</head>

<div id="search">
<form method="get" action="${ctx}/collection/search" id="searchForm">
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
<c:forEach var="collection" items="${collectionList}">
<c:set var="collection" value="${collection}"/>

<div id="result">
<i>Name:</i> <a href="/collection/get?id=${collection.id}">${collection.name} </a> (<%=getSize(pageContext)%>) songs<br/>
<i>Description:</i> ${collection.description}

</div>
<br/>
</c:forEach>
</div>
<%!
int getSize(PageContext pageContext){
	SongCollection collection = (SongCollection)pageContext.getAttribute("collection");
	return collection.getSongs().size();
}

%>