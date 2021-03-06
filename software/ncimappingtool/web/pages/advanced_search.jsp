<%--L
  Copyright Northrop Grumman Information Technology.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/nci-mapping-tool/LICENSE.txt for details.
L--%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.*"%>
<%@ page import="org.LexGrid.concepts.Entity" %>
<%@ page import="gov.nih.nci.evs.browser.utils.*" %>
<%@ page import="gov.nih.nci.evs.browser.bean.*" %>

<%
  String ncit_build_info = new DataUtils().getNCITBuildInfo();
  String application_version = new DataUtils().getApplicationVersion();
  String anthill_build_tag_built = new DataUtils().getNCITAnthillBuildTagBuilt();
  String evs_service_url = new DataUtils().getEVSServiceURL();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns:c="http://java.sun.com/jsp/jstl/core">
<head>
  <title>NCI EVS Concept Mapping Tool</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/styleSheet.css" />
  <link rel="shortcut icon" href="<%= request.getContextPath() %>/favicon.ico" type="image/x-icon" />
  <script type="text/javascript" src="<%= request.getContextPath() %>/js/script.js"></script>
  <script type="text/javascript" src="<%= request.getContextPath() %>/js/search.js"></script>
  <script type="text/javascript" src="<%= request.getContextPath() %>/js/dropdown.js"></script>
</head>
<body>
<!--
   Build info: <%=ncit_build_info%>
 Version info: <%=application_version%>
          Tag: <%=anthill_build_tag_built%>
   LexEVS URL: <%=evs_service_url%>          
  -->
  <script type="text/javascript" src="<%= request.getContextPath() %>/js/wz_tooltip.js"></script>
  <script type="text/javascript" src="<%= request.getContextPath() %>/js/tip_centerwindow.js"></script>
  <script type="text/javascript" src="<%= request.getContextPath() %>/js/tip_followscroll.js"></script>

<%
String basePath = request.getContextPath(); 
String message = (String) request.getSession().getAttribute("message");

request.getSession().removeAttribute("message");

String identifier = (String) request.getSession().getAttribute("identifier");
if (identifier != null && identifier.compareTo("null") == 0) {
    identifier = "";
} else if (identifier == null) {
    identifier = "";
}


String mapping_version = (String) request.getSession().getAttribute("mapping_version");
String type = (String) request.getSession().getAttribute("type");

String source_cs = (String) request.getSession().getAttribute("source_cs");
String target_cs = (String) request.getSession().getAttribute("target_cs");
String input_option = "Property";//(String) request.getSession().getAttribute("input_option");
if (input_option == null) {
    input_option = "Name";
}

String input_option_label = input_option;
//input_option_label = input_option_label.toLowerCase();

String algorithm = (String) request.getSession().getAttribute("algorithm");
if (algorithm != null && algorithm.compareTo("null") == 0) {
    algorithm = "";
} else if (DataUtils.isNull(algorithm)) {
    algorithm = "exactMatch";
}

String source_scheme = DataUtils.key2CodingSchemeName(source_cs);
String source_version = DataUtils.key2CodingSchemeVersion(source_cs);


String target_scheme = DataUtils.key2CodingSchemeName(target_cs);
String target_version = DataUtils.key2CodingSchemeVersion(target_cs);


Vector properties = OntologyBean.getSupportedPropertyNames(target_scheme, target_version);

Vector src_properties = OntologyBean.getSupportedPropertyNames(source_scheme, source_version);
Vector target_properties = OntologyBean.getSupportedPropertyNames(target_scheme, target_version);


String property = (String) request.getSession().getAttribute("property");
if (property != null && property.compareTo("null") == 0) {
    property = "";
} else if (property == null) {
    property = "";
}

String[] src_property = (String[]) request.getSession().getAttribute("src_property");
if (src_property == null) {
    src_property = new String[1];
    src_property[0] = (String) src_properties.elementAt(0);
}


String target_property = (String) request.getSession().getAttribute("target_property");
if (target_property != null && target_property.compareTo("null") == 0) {
    target_property = (String) target_properties.elementAt(0);
} else if (target_property == null) {
    target_property = "";
}



%>
<f:view>
  <!-- Begin Skip Top Navigation -->
    <a href="#evs-content" class="hideLink" accesskey="1" title="Skip repetitive navigation links">skip navigation links</A>
  <!-- End Skip Top Navigation -->
  <%@ include file="/pages/templates/header.jsp" %>
  <div class="center-page">
    <%@ include file="/pages/templates/sub-header.jsp" %>
    <!-- Main box -->
    <div id="main-area">
      <%@ include file="/pages/templates/content-header.jsp" %>
      <!-- Page content -->
      <div class="pagecontent">
        <a name="evs-content" id="evs-content"></a>


<h:form>        

    <p class="texttitle-blue">Advanced Search</p>

  
  <table border="0" width="700px">
  

            <% if (message != null) { request.getSession().removeAttribute("message"); %>
              <tr class="textbodyred">
                <td>
                <p class="textbodyred">&nbsp;<%=message%></p>
                </td>              
                <td></td>
              </tr>
              <tr><td>&nbsp;</td></tr>
              
            <% } %>       
  

                  <tr valign="top" align="left">
                          <td align="left" class="textbody">
                              <b>Identifier</b>:&nbsp;<%=identifier%>
                          </td>
                          <td>
                      &nbsp;
                          </td>
                  </tr>
                  
                <tr>
		  <td align="left" class="textbody">
		      <b>From</b>:&nbsp;<%=source_cs%> 
		  </td>
                          <td>
                      &nbsp;
                          </td>
                </tr>
                
                <tr>
		  <td align="left" class="textbody">
		      <b>To</b>:&nbsp;<%=target_cs%> 
		  </td>
                          <td>
                      &nbsp;
                          </td>
                </tr>
                
                <tr>
		  <td align="left" class="textbody">
		      <b>Input Option</b>:&nbsp;<%=input_option%> 
		  </td>
                          <td>
                      &nbsp;
                          </td>
                </tr>                

     </table>
     
    
     <table>
<%     
    MappingUtils util = new MappingUtils();
    Vector algorithms = util.getSupportedSearchTechniqueNames();
    String left_trim = "";
    String right_trim = "";
    String prefix = "";
    String suffix = "";
    
%>    

                <tr>
		  <td align="left" class="textbody">
		      <b>Algorithm</b>:
		  </td>

                  <td class="textbody">
                    <select id="algorithm" name="algorithm" size="1" tabindex="4">
                    <%
                       if (algorithms != null) {
			    for (int i=0; i<algorithms.size(); i++) {
				 String t = (String) algorithms.elementAt(i);
				 if (t.compareTo(algorithm) == 0) {
			    %>
				   <option value="<%=t%>" selected><%=t%></option>
			    <%
				 } else {
			    %>
				   <option value="<%=t%>"><%=t%></option>
			    <%
				 }
			    }
                       }
                    %>
                    </select>
                </td></tr>

<%
if (input_option.compareToIgnoreCase("Property") == 0) { 

    Set<String> values = new HashSet<String>(Arrays.asList(src_property));

%>
                 <tr>
		  <td align="left" class="textbody">
		      <b>Source Properties</b>:
		  </td>

                  <td class="textbody">
                    <select id="src_property" name="src_property" size="5" tabindex="4" multiple>
                    <%
                       if (src_properties != null) {
			    for (int i=0; i<src_properties.size(); i++) {
				 String t = (String) src_properties.elementAt(i);
				 //if (t.compareTo(src_property) == 0) {
				 if (values.contains(t)) {
			    %>
				   <option value="<%=t%>" selected><%=t%></option>
			    <%
				 } else {
			    %>
				   <option value="<%=t%>"><%=t%></option>
			    <%
				 }
			    }
                       }
                    %>
                    </select>
                </td></tr>
                

                 <tr>
		  <td align="left" class="textbody">
		      <b>Target Property</b>:
		  </td>

                  <td class="textbody">
                    <select id="target_property" name="target_property" size="1" tabindex="4">
                    <%
                       if (target_properties != null) {
			    for (int i=0; i<target_properties.size(); i++) {
				 String t = (String) target_properties.elementAt(i);
				 if (t.compareTo(target_property) == 0) {
			    %>
				   <option value="<%=t%>" selected><%=t%></option>
			    <%
				 } else {
			    %>
				   <option value="<%=t%>"><%=t%></option>
			    <%
				 }
			    }
                       }
                    %>
                    </select>
                </td></tr>
                
 <tr><td>&nbsp;</td><td></td></tr>
 
                  <tr>
 		  <td align="left" class="textbody" colspan = "2">
 		      <b>Rules for converting source property to target property format (optional)</b>:
		  </td>
		  </tr>
		  
		  
                <tr>
		  <td align="right" class="textbody">
		      <b>Left Trim</b>:&nbsp; 
		  </td>
                  <td>
                      <input CLASS="searchbox-input" id="left_trim" name="left_trim" value="<%=left_trim%>" width="700px" tabindex="1"/>
                  </td>
                </tr> 		  
                
                 <tr>
		  <td align="right" class="textbody">
		      <b>Right Trim</b>:&nbsp; 
		  </td>
                  <td>
                      <input CLASS="searchbox-input" id="right_trim" name="right_trim" value="<%=right_trim%>" width="700px" tabindex="1"/>
                  </td>
                </tr>                

                <tr>
		  <td align="right" class="textbody">
		      <b>Add Prefix</b>:&nbsp; 
		  </td>
                  <td>
                      <input CLASS="searchbox-input" id="prefix" name="prefix" value="<%=prefix%>" width="700px" tabindex="1"/>
                  </td>
                </tr> 		  
                
                 <tr>
		  <td align="right" class="textbody">
		      <b>Add Suffix</b>:&nbsp; 
		  </td>
                  <td>
                      <input CLASS="searchbox-input" id="suffix" name="suffix" value="<%=suffix%>" width="700px" tabindex="1"/>
                  </td>
                </tr> 

<tr><td>&nbsp;</td><td></td></tr>                
<%
}
%>
     
<!--     
     
     <tr>               
     <%
     if (input_option.compareTo("Name") == 0) {
     %>
                          <td align="left" class="textbody">
                              <b><%=input_option_label%>&nbsp;data:</b>
                          </td>
     <%                     
     } else {
     %>
                          <td align="left" class="textbody">
                              <b>Source code:</b>
                          </td>
     <%
     }
     %>
                          
                          
     <td>  
         <textarea name="codes" cols="50" rows=10 tabindex="3"></textarea>
     </td>
     </tr>
     
-->


  	
  	
                  <tr><td>

  	<h:commandButton
  		id="SubmitForm"
  		value="SubmitForm"
  		image="#{basePath}/images/submit.gif"
  		action="#{mappingBean.submitBatchAction}" 
  		alt="Submit Batch" >
  	</h:commandButton>

                  &nbsp;


      <a href="#" onclick="javascript:history.back();" >
        <img src="<%= request.getContextPath() %>/images/cancel.gif" style="border: none">
      </a> 
      
      
                  
                  </td>

                  <td>
                  
                  
                  
                  </td>
                  </tr>	
	
     </table> 

     <input type="hidden" name="type" id="type" value="<%=type%>">
     
     <input type="hidden" name="identifier" id="identifier" value="<%=identifier%>">
     <input type="hidden" name="mapping_version" id="mapping_version" value="<%=mapping_version%>">
     
     
     <input type="hidden" name="source_cs" id="source_cs" value="<%=source_cs%>">
     <input type="hidden" name="target_cs" id="target_cs" value="<%=target_cs%>">
     
     <input type="hidden" name="refresh" id="refresh" value="true">
     
     <input type="hidden" name="input_option" id="input_option" value="Property">
     <input type="hidden" name="advanced" id="advanced" value="true">
     


</h:form>

      
        
        
        <%@ include file="/pages/templates/nciFooter.jsp" %>
      </div>
      <!-- end Page content -->
    </div>
    <div class="mainbox-bottom"><img src="<%=basePath%>/images/mainbox-bottom.gif" width="745" height="5" alt="Mainbox Bottom" /></div>
    <!-- end Main box -->
  </div>
</f:view>
</body>
</html>
