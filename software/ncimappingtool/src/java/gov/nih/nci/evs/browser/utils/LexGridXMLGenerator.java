/*L
 * Copyright Northrop Grumman Information Technology.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/nci-mapping-tool/LICENSE.txt for details.
 */

package gov.nih.nci.evs.browser.utils;

import gov.nih.nci.evs.browser.bean.*;
import java.io.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import java.util.*;

/**
  * 
  */

/**
  * @author EVS Team
  * @version 1.0
  *
  * Modification history
  *     Initial implementation kim.ong@ngc.com
  *
 */

public class LexGridXMLGenerator {

  public LexGridXMLGenerator() {


  }

  public static void writeHeader(PrintWriter out, String identifier, String version,
                                           String source_cs, String source_cs_uri,
                                           String target_cs, String target_cs_uri) {

      out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      out.println("<codingScheme xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
      out.println("  xsi:schemaLocation=\"http://LexGrid.org/schema/2010/01/LexGrid/codingSchemes  https://ncisvn.nci.nih.gov/svn/lexevs/base/v6/trunk/lexgrid_model/lgModel/master/codingSchemes.xsd\"");
      out.println("  xmlns=\"http://LexGrid.org/schema/2010/01/LexGrid/codingSchemes\"");
      out.println("  xmlns:lgBuiltin=\"http://LexGrid.org/schema/2010/01/LexGrid/builtins\"");
      out.println("  xmlns:lgCommon=\"http://LexGrid.org/schema/2010/01/LexGrid/commonTypes\"");
      out.println("  xmlns:lgCon=\"http://LexGrid.org/schema/2010/01/LexGrid/concepts\"");
      out.println("  xmlns:lgRel=\"http://LexGrid.org/schema/2010/01/LexGrid/relations\"");
      out.println("  xmlns:lgCS=\"http://LexGrid.org/schema/2010/01/LexGrid/codingSchemes\"");
      out.println("  xmlns:lgLDAP=\"http://LexGrid.org/schema/2010/01/LexGrid/ldap\"");
      out.println("  xmlns:lgNaming=\"http://LexGrid.org/schema/2010/01/LexGrid/naming\"");
      out.println("  xmlns:lgService=\"http://LexGrid.org/schema/2010/01/LexGrid/service\"");
      out.println("  xmlns:lgVD=\"http://LexGrid.org/schema/2010/01/LexGrid/valueDomains\"");
      out.println("  xmlns:lgVer=\"http://LexGrid.org/schema/2010/01/LexGrid/versions\"");
      out.println("  xmlns:NCIHistory=\"http://LexGrid.org/schema/2010/01/LexGrid/NCIHistory\"");
      out.println("  approxNumConcepts=\"0\" codingSchemeName=\"" + identifier + "\" defaultLanguage=\"en\" formalName=\"" + identifier + "\" codingSchemeURI=\"urn:oid:" + identifier + "\" representsVersion=\"" + version + "\">");
      out.println("  <lgCommon:entityDescription>" + identifier + "</lgCommon:entityDescription>");
      out.println("  <localName>" + identifier + "</localName>");
      out.println("  <source subRef=\"testSubRef\" role=\"testRole\">lexgrid.org</source>");
      out.println("  <copyright>Copyright by Mayo Clinic.</copyright>");
      out.println("  <mappings>");
      out.println("    <lgNaming:supportedAssociation localId=\"hasSubtype\" uri=\"urn:oid:1.3.6.1.4.1.2114.108.1.8.1\">hasSubtype</lgNaming:supportedAssociation>");
      out.println("    <lgNaming:supportedAssociationQualifier localId=\"rel\" uri=\"www.rel.com\">rel</lgNaming:supportedAssociationQualifier>");
      out.println("    <lgNaming:supportedAssociationQualifier localId=\"score\" uri=\"www.score.com\">score</lgNaming:supportedAssociationQualifier>");
      out.println("    <lgNaming:supportedCodingScheme localId=\"" + identifier + "\" uri=\"urn:oid:mapping:sample\">" + identifier + "</lgNaming:supportedCodingScheme>");
      out.println("    <lgNaming:supportedCodingScheme localId=\"" + target_cs + "\" uri=\"" + target_cs_uri + "\">" + target_cs + "</lgNaming:supportedCodingScheme>");
      out.println("    <lgNaming:supportedCodingScheme localId=\"" + source_cs + "\" uri=\"" + source_cs_uri + "\">" + source_cs + "</lgNaming:supportedCodingScheme>");
      out.println("    <lgNaming:supportedContainerName localId=\"" + identifier + "\">" + identifier + "</lgNaming:supportedContainerName>");
      out.println("    <lgNaming:supportedDataType localId=\"testhtml\">test/html</lgNaming:supportedDataType>");
      out.println("    <lgNaming:supportedDataType localId=\"textplain\">text/plain</lgNaming:supportedDataType>");
      out.println("    <lgNaming:supportedHierarchy localId=\"is_a\" associationNames=\"hasSubtype\" isForwardNavigable=\"true\" rootCode=\"@\">hasSubtype</lgNaming:supportedHierarchy>");
      out.println("    <lgNaming:supportedLanguage localId=\"en\" uri=\"www.en.org/orsomething\">en</lgNaming:supportedLanguage>");
      out.println("    <lgNaming:supportedNamespace localId=\"" + target_cs + "\" uri=\"" + target_cs_uri + "\" equivalentCodingScheme=\"" + target_cs + "\">" + target_cs + "</lgNaming:supportedNamespace>");
      out.println("    <lgNaming:supportedNamespace localId=\"" + source_cs + "\" uri=\"" + source_cs_uri + "\" equivalentCodingScheme=\"" + source_cs + "\">" + source_cs + "</lgNaming:supportedNamespace>");
      out.println("  </mappings>");
   }




  public static void writeContent(PrintWriter out, String identifier,
                                  String source_cs, String target_cs,
                                  List<MappingData> list) {

      out.println("  <relations containerName=\"" + identifier + "\" sourceCodingScheme=\"" + source_cs + "\" targetCodingScheme=\"" + target_cs + "\" isMapping=\"true\">");
      out.println("    <lgCommon:entityDescription>" + identifier + "</lgCommon:entityDescription>");
      out.println("    <lgRel:associationPredicate associationName=\"mapsTo\">");

      if (list != null) {
		  for (int i=0; i<list.size(); i++) {
              MappingData md = (MappingData) list.get(i);
              writeContent(out, md);
		  }
  	  }

      out.println("     </lgRel:associationPredicate>");
      out.println("   </relations>");
      out.println("</codingScheme>        ");
  }


  public static void writeContent(PrintWriter out, MappingData md) {
	  if (md == null) return;

	  String source_ns = md.getSourceCodeNamespace();
	  String source_code = md.getSourceCode();
	  String rel = md.getRel();
	  int score_int = md.getScore();
	  String score = Integer.valueOf(score_int).toString();
	  String target_ns = md.getTargetCodeNamespace();
	  //String target_cs = md.getTargetCodingScheme();
	  String target_code = md.getTargetCode();

      out.println("        <lgRel:source sourceEntityCodeNamespace=\"" + source_ns + "\" sourceEntityCode=\"" + source_code + "\">");
      out.println("          <lgRel:target targetEntityCodeNamespace=\"" + target_ns + "\" targetEntityCode=\"" + target_code + "\">");
      out.println("            <lgRel:associationQualification associationQualifier=\"rel\">");
      out.println("              <lgRel:qualifierText>" + rel + "</lgRel:qualifierText>");
      out.println("            </lgRel:associationQualification>");
      out.println("            <lgRel:associationQualification associationQualifier=\"score\">");
      out.println("              <lgRel:qualifierText>" + score + "</lgRel:qualifierText>");
      out.println("            </lgRel:associationQualification>");
      out.println("          </lgRel:target>");
      out.println("        </lgRel:source>");

  }

/*
   public void test() {
	   String identifier = "GO_to_NCIt_Mapping";
	   String version = "1.0";
       String source_cs = "NCI_Thesaurus";
       String source_cs_uri = "urn:lsid:bioontology.org:GO";
       String target_cs = "GO";
       String target_cs_uri = "urn:lsid:bioontology.org:GO";

       PrintWriter pw = new PrintWriter(System.out, true);

       writeHeader(pw, identifier, version,
                   source_cs, source_cs_uri,
                   target_cs, target_cs_uri);
   }

   public static void main(String[] args) throws Exception {
	   LexGridXMLGenerator test = new LexGridXMLGenerator();
	   test.test();

   }
*/
}


