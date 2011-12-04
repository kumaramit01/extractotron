<%@ include file="/common/taglibs.jsp"%>
<tr>
    <td>
        <select name="<c:out value="${param.rightId}"/>" 
            id="<c:out value="${param.rightId}"/>" size="5">
        <c:forEach var="list" items="${leftList}" varStatus="status">
            <option value="">Select</option>
            <option value="<c:out value="${list.value}"/>" 
             <c:forEach var="x" items="${rightList}">
               <c:if test="${list.value == x.value}">selected=selected</c:if>
             </c:forEach>
             >
               <c:out value="${list.label}" escapeXml="false" />
            </option>
        </c:forEach>
        </select>
    </td>
    

            
   
</tr>
<table>
<thead><b>Features</b></thead>
<tr>
<td>
<select name="selectedFeature" id="selectedFeature" size="5">
	<c:forEach var="feature" items="${supportedFeatures}" varStatus="status">
		<option value="<c:out value="${feature.value}"/>" 
		<c:if test="${feature.value == currentFeature}">selected=selected</c:if>">
		<c:out value="${feature.label}" escapeXml="false"/>
		</option>
	</c:forEach>
</select>
</td>

</tr>
<tr>

<td>
    <div class="left">
        <appfuse:label styleClass="desc" key="project.commandLine"/>
        <form:errors path="commandLine" cssClass="fieldError"/>
        <form:textarea path="commandLine" id="commandLine" cssClass="text large" cssErrorClass="text large error"/>
    </div>

</td>

</tr>
</table>


<script type="text/javascript">
var selectedExtractor = document.getElementById("selectedExtractor");
selectedExtractor.onchange=function(){
	var chosenoption=this.options[this.selectedIndex] //this refers to "selectmenu"
	var command = document.getElementById("commandLine");
	var commandLineValue=command.value;
	var tokens = chosenoption.value.split(" ")
	var found = false;
	for(var i=0; i < tokens.length; i++){
		if(found){
			tokens[i] =getValue();
			break;
		}
		if(tokens[i] == "-fe"){
		  found = true;	
		}
	}
	command.value = tokens.join(" ");
}

function getValue(){
	var selectmenu=document.getElementById("selectedFeature")
	if(selectmenu.selectedIndex==-1)
		return "";
	var chosenoption=selectmenu.options[selectmenu.selectedIndex]
	return chosenoption.value;
}


var selectmenu=document.getElementById("selectedFeature")
selectmenu.onchange=function(){ //run some code when "onchange" event fires
var chosenoption=this.options[this.selectedIndex] //this refers to "selectmenu"
//alert(chosenoption.value);

var command = document.getElementById("commandLine");
var commandLineValue=command.value;

if(!commandLineValue){
 alert("Select the Extractor First before selecting a Feature");
}else{

	var tokens = commandLineValue.split(" ")
	var found = false;
	var replaced = false;
	for(var i=0; i < tokens.length; i++){
		if(found){
			tokens[i] = chosenoption.value;
			replaced=true;
			break;
		}
		if(tokens[i] == "-fe"){
		  found = true;	
		}
	}
	if(replaced){
		commandLineValue = tokens.join(" ");
		command.value = commandLineValue;
	}
	
}
 
}

</script>
