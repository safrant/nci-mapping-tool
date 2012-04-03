package gov.nih.nci.evs.browser.servlet;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import gov.nih.nci.evs.browser.bean.*;
import gov.nih.nci.evs.browser.utils.*;



public class MappingServlet extends HttpServlet {

   public void init(ServletConfig config)
	           throws ServletException
   {
      super.init(config);
   }

   public void doGet(HttpServletRequest request,
                     HttpServletResponse response)
              throws IOException, ServletException
   {
        execute(request, response);
   }


   public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
              throws IOException, ServletException
   {
        execute(request, response);
   }

   public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

		String action = (String) request.getParameter("action");
		String format = (String) request.getParameter("format");

		System.out.println("(*) ACTION: " + action);
		System.out.println("(*) FORMAT: " + format);


		if (action.compareTo("export") == 0 && format.compareTo("xml") == 0) {
			 exportMappingToXMLAction(request, response);
		} else if (action.compareTo("export") == 0 && format.compareTo("excel") == 0) {
			 exportMappingToExcelAction(request, response);
		} else if (action.compareTo("export") == 0 && format.compareTo("lexgrid_xml") == 0) {
			 exportMappingToLexGridAction(request, response);
		}

   }








    public void exportMappingToExcelAction(HttpServletRequest request, HttpServletResponse response ) {

System.out.println("exportMappingToMSExcelAction ...");

        new MappingBean().updateMapping(request);
		String type = (String) request.getParameter("type");

        try {

			long ms = System.currentTimeMillis();

String key = (String) request.getParameter("key");
System.out.println("key: " + key);


            String[] entry_status = request.getParameterValues("entry_status");


			HashMap mappings = (HashMap) request.getSession().getAttribute("mappings");
			if (mappings == null) {
				System.out.println("exportMappingToMSExcelAction mappings == null ???");

				mappings = new HashMap();
				request.getSession().setAttribute("mappings", mappings);
			}

			HashMap status_hmap = (HashMap) request.getSession().getAttribute("status_hmap");
			MappingObject obj = (MappingObject) mappings.get(key);

if (obj == null) {
	System.out.println("mapping obj cannot be found for key : " + key);

}

			String identifier = obj.getName();
			//String mapping_version = obj.getVersion();


			//String identifier = (String) request.getParameter("identifier");
			if (identifier == null) {
				identifier = "mapping";
			}
			identifier = identifier + ".xls";

			response.setContentType("application/vnd.ms-excel");

			response.setHeader("Content-Disposition", "attachment; filename="
						+ identifier);

              PrintWriter out = response.getWriter();

			  out.println("<table>");
			  out.println("   <th class=\"dataTableHeader\" width=\"60px\" scope=\"col\" align=\"left\">Source</th>");
			  out.println("   <th class=\"dataTableHeader\" scope=\"col\" align=\"left\">");
			  out.println("	  Source Code");
			  out.println("   </th>");
			  out.println("   <th class=\"dataTableHeader\" scope=\"col\" align=\"left\">");
			  out.println("	  Source Name");
			  out.println("   </th>");
			  out.println("   <th class=\"dataTableHeader\" width=\"30px\" scope=\"col\" align=\"left\">");
			  out.println("	  REL");
			  out.println("   </th>");
			  out.println("   <th class=\"dataTableHeader\" width=\"35px\" scope=\"col\" align=\"left\">");
			  out.println("	  Map Rank");
			  out.println("   </th>");
			  out.println("   <th class=\"dataTableHeader\" width=\"60px\" scope=\"col\" align=\"left\">Target</th>");
			  out.println("   <th class=\"dataTableHeader\" scope=\"col\" align=\"left\">");
			  out.println("	  Target Code");
			  out.println("   </th>");
			  out.println("   <th class=\"dataTableHeader\" scope=\"col\" align=\"left\">");
			  out.println("	  Target Name");
			  out.println("   </th>");
			  out.println("</table>");

			  // Add mapping entries based on selected entry categories here -- to be implemented.


			  out.flush();
			  out.close();


		} catch (Exception ex) {
			ex.printStackTrace();
		}

  }






    public void exportMappingToXMLAction(HttpServletRequest request, HttpServletResponse response ) {
        new MappingBean().updateMapping(request);
		String type = (String) request.getParameter("type");

        try {
        	//String xml = null;
			StringBuffer sb = null;
			response.setContentType("text/xml");

String key = (String) request.getParameter("key");
System.out.println("key: " + key);

String format = (String) request.getParameter("format");

            String[] entry_status = request.getParameterValues("entry_status");

			HashMap mappings = (HashMap) request.getSession().getAttribute("mappings");
			if (mappings == null) {
				System.out.println("exportMappingToXMLAction mappings == null ???");

				mappings = new HashMap();
				request.getSession().setAttribute("mappings", mappings);
			}

			HashMap status_hmap = (HashMap) request.getSession().getAttribute("status_hmap");
			MappingObject obj = (MappingObject) mappings.get(key);

			if (obj == null) {
				System.out.println("mapping obj cannot be found for key : " + key);
			}

			String mapping_name = obj.getName();
			String mapping_version = obj.getVersion();
			String xml = "";

			if (obj != null) {
				obj.setStatusHashMap(status_hmap);

				sb = new StringBuffer(xml);
				sb = sb.append(obj.toXML());
			}

			mapping_name = mapping_name.replaceAll(" ", "_");
			mapping_name = mapping_name + ".xml";

			response.setHeader("Content-Disposition", "attachment; filename="
					+ mapping_name);

System.out.println("mapping file name : " + mapping_name);

System.out.println("mapping sb.length() : " + sb.length());

			response.setContentLength(sb.length());

			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(sb.toString().getBytes(), 0, sb.length());
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}




    public void exportMappingToLexGridAction(HttpServletRequest request, HttpServletResponse response ) {
        new MappingBean().updateMapping(request);
		String type = (String) request.getParameter("type");

        try {
        	//String xml = null;
			StringBuffer sb = null;
			response.setContentType("text/xml");

String key = (String) request.getParameter("key");
System.out.println("key: " + key);

String format = (String) request.getParameter("format");

			HashMap mappings = (HashMap) request.getSession().getAttribute("mappings");
			if (mappings == null) {
				System.out.println("exportMappingToXMLAction mappings == null ???");

				mappings = new HashMap();
				request.getSession().setAttribute("mappings", mappings);
			}

			HashMap status_hmap = (HashMap) request.getSession().getAttribute("status_hmap");
			MappingObject obj = (MappingObject) mappings.get(key);

			String mapping_name = obj.getName();
			String mapping_version = obj.getVersion();

			mapping_name = mapping_name.replaceAll(" ", "_");
			mapping_name = mapping_name + ".xml";

			response.setHeader("Content-Disposition", "attachment; filename="
					+ mapping_name);

            PrintWriter out = response.getWriter();

            String source_cs_uri = DataUtils.codingSchemeName2URI(obj.getFromCS());//"source_cs_uri_to_be_determined";
            String target_cs_uri = DataUtils.codingSchemeName2URI(obj.getToCS());//"target_cs_uri_to_be_determined";

  			LexGridXMLGenerator.writeHeader(out, obj.getName(), obj.getVersion(),
                                       		obj.getFromCS(), source_cs_uri,
                                       		obj.getToCS(), target_cs_uri);

            Vector options = new Vector();

            //String[] entry_status = request.getParameterValues("entry_status");

            options.add("Valid");
            //options.add("Invalid");
            List<MappingData> list = obj.getMappingData(options);

            LexGridXMLGenerator.writeContent(out, obj.getName(),
                                             obj.getFromCS(), obj.getToCS(), list);

			out.flush();
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}


