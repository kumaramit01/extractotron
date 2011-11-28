<%@ include file="/common/taglibs.jsp"%>



<head>
    <title><fmt:message key="collection.title"/></title>
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
<c:set var="collection" value="${collection}"/>

<div id="result">
<i>Name:</i> <a href="/collection/get?id=${collection.id}">${collection.name} </a> (<%=getSize(pageContext)%>) songs<br/>
<br/>
<i>Description:</i> ${collection.description}
</div>
<br/>
<br/>

<div>

<h6>Songs in ${collection.name}</h6>

<% request.setAttribute("songDetails",getSongs(pageContext));%>


<display:table name="songDetails" cellspacing="0" cellpadding="0" requestURI="" 
    defaultsort="1" id="songs" pagesize="25" class="table" export="true">
    <display:column sortProperty="name" property="name" escapeXml="true" sortable="true" titleKey="song.name" style="width: 25%"
        url="/song?from=list" paramId="id" paramProperty="id"/>
    <display:setProperty name="paging.banner.item_name" value="song"/>
    <display:setProperty name="paging.banner.items_name" value="songs"/>

    <display:setProperty name="export.excel.filename" value="User List.xls"/>
    <display:setProperty name="export.csv.filename" value="User List.csv"/>
    <display:setProperty name="export.pdf.filename" value="User List.pdf"/>
</display:table>

</div>


</div>

<%!
int getSize(PageContext pageContext){
	SongCollection collection = (SongCollection)pageContext.getAttribute("collection");
	return collection.getSongs().size();
}
Set<Song> getSongs(PageContext pageContext){
	SongCollection collection = (SongCollection)pageContext.getAttribute("collection");
	return collection.getSongs();
}

%>