<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="mainMenu.title"/></title>
    <meta name="heading" content="<fmt:message key='mainMenu.heading'/>"/>
    <meta name="menu" content="MainMenu"/>
</head>

<p><fmt:message key="mainMenu.message"/></p>

<div class="separator"></div>

<ul class="glassList">
    <li>
        <a href="<c:url value='/projectform?method=Add'/>"><fmt:message key="menu.project.new"/></a>
    </li>
    <li>
        <a href="<c:url value='/collection/list'/>"><fmt:message key="menu.collection.list"/></a>
    </li>
    <li>
    	<a href="<c:url value='/extractor/list'/>"><fmt:message key="menu.extractor.list"/></a>
    </li>
</ul>
