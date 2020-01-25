package eg.edu.alexu.csd.oop.db;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Schema {
public void createSchema(String dir,String name,ArrayList<String > colum) {
	try {
		File f=new File(dir+System.getProperty("file.separator")+name+".xsd");
		 DocumentBuilderFactory documentFactory =DocumentBuilderFactory.newInstance();
	      DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
	      Document document =documentBuilder.newDocument();
	      Element root=document.createElement("xs:schema");
	      document.appendChild(root);
	      Element table=document.createElement("xs:element");
	      root.appendChild(table);
	      Attr attr=document.createAttribute("name");
	      attr.setValue(name);
	      table.setAttributeNode(attr);
	      Element type=document.createElement("xs:complexType");
	      table.appendChild(type);
	      Element seq=document.createElement("xs:sequence");
	      type.appendChild(seq);
	      for(int i=0;i<colum.size();i++) {
	      Element col=document.createElement("xs:element");
		  seq.appendChild(col);
		  Attr attr2=document.createAttribute("name");
	      attr2.setValue(colum.get(i));
	      col.setAttributeNode(attr2);
	      Element type2=document.createElement("xs:complexType");
	      col.appendChild(type2);
	      Element seq2=document.createElement("xs:sequence");
	      type2.appendChild(seq2);
	      Element attType=document.createElement("xs:attribute");
	      col.appendChild(attType);
	      Attr attr3=document.createAttribute("name");
	      attr3.setValue("type");
	      attType.setAttributeNode(attr3);
	      Attr attr4=document.createAttribute("type");
	      attr4.setValue("xs:String");
	      attType.setAttributeNode(attr4);}
	      try {
	    	  Transformer transformer=TransformerFactory.newInstance().newTransformer();
				Source source=new DOMSource(document);
				Result result=new StreamResult(f);
				transformer.transform(source, result);
	     
	      } catch (Exception e) {
				// TODO: handle exception
			} 
	     } catch (ParserConfigurationException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
        	} 
}
public void deleteXsd(String dir,String name) {
	File f=new File(dir+System.getProperty("file.separator")+name+".xsd");
	f.delete();
}
public void insertXsd(String dir,String name,int n,ArrayList<String> columns) {
	try {
		File f=new File(dir+System.getProperty("file.separator")+name+".xsd");
		 DocumentBuilderFactory documentFactory =DocumentBuilderFactory.newInstance();
	     DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
	     Document document =documentBuilder.parse(f);
	     Node schema=document.getFirstChild();
	     Node table=schema.getFirstChild();
	     Node type=table.getFirstChild();
	     Node seq=type.getFirstChild();
	     Node col=seq.getChildNodes().item(n);
	     Node type2=col.getFirstChild();
	     Node seq2=type2.getFirstChild();
	     NodeList temp=seq2.getChildNodes();
    	 while(seq2.hasChildNodes()) {
    		 seq2.removeChild(temp.item(0));
    	 }
	     for(int i=0;i<columns.size();i++) {
		     Element r=document.createElement("xs:element");
		     seq2.appendChild(r);
		     Attr attr=document.createAttribute("name");
		     attr.setValue("id"+Integer.toString(i));
		     r.setAttributeNode(attr);
		     Attr attr2=document.createAttribute("type");
		     attr2.setValue("xs:String");
		     r.setAttributeNode(attr2);
		     }
	     try {
	   	  Transformer transformer=TransformerFactory.newInstance().newTransformer();
				Source source=new DOMSource(document);
				Result result=new StreamResult(f);
				transformer.transform(source, result);
	    
	     } catch (Exception e) {
				// TODO: handle exception
			} 
	} catch (Exception e) {
		// TODO: handle exception
	}
	}
public void removeXsd(String dir,String name) {
	try {
		
	File f=new File(dir+System.getProperty("file.separator")+name+".xsd");
	 DocumentBuilderFactory documentFactory =DocumentBuilderFactory.newInstance();
     DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
     Document document =documentBuilder.parse(f);
     Node schema=document.getFirstChild();
     Node table=schema.getFirstChild();
     Node type=table.getFirstChild();
     Node seq=type.getFirstChild();
     NodeList cols=seq.getChildNodes();
     for(int i=0;i<cols.getLength();i++) {
    	 Node temp=cols.item(i);
    	 Node type2=temp.getFirstChild();
	     Node seq2=type2.getFirstChild();
	     NodeList temp2=seq2.getChildNodes();
    	 while(seq2.hasChildNodes()) {
    		 seq2.removeChild(temp2.item(0));
    	 }
     }
     try {
	   	  Transformer transformer=TransformerFactory.newInstance().newTransformer();
				Source source=new DOMSource(document);
				Result result=new StreamResult(f);
				transformer.transform(source, result);
	    
	     } catch (Exception e) {
				// TODO: handle exception
			} 
	} catch (Exception e) {
		// TODO: handle exception
	}
}
}
