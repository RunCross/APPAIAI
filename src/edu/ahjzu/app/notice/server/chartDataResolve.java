package edu.ahjzu.app.notice.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.ahjzu.app.notice.pojo.Chat;




public class chartDataResolve {
	  DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance(); 
      List<Chat> list=new ArrayList<Chat>();
      InputStream is=null;
      public chartDataResolve(InputStream is){
    	  this.is=is;
      }
      
      public List<Chat> getDatas(){
    	  Document document =parse(is);
    	  NodeList child1s = document.getChildNodes(); 
          for(int i=0;i<child1s.getLength();i++){
         	 Node child1 = child1s.item(i); 
         	 NodeList child2s = child1.getChildNodes(); 
         	 for (int j = 0; j < child2s.getLength(); j++) { 
         			Node child2 = child2s.item(j); 
         			if(child2.getNodeType()==Node.ELEMENT_NODE)
         			{
         			NodeList child3s = child2.getChildNodes();
         			Chat data=new Chat();
         			for (int k = 0; k < child3s.getLength(); k++) {
         				
         				Node child3=child3s.item(k);
         				if(child3.getNodeType()==Node.ELEMENT_NODE)
         				{
         					if(child3.getNodeName().equals("id"))
         						data.setid(child3.getTextContent());
         					else	if(child3.getNodeName().equals("addr_Longitude"))
         						data.setaddr_Longitude(Float.valueOf(child3.getTextContent()));
         					else	if(child3.getNodeName().equals("addr_Latitude"))
         					    data.setaddr_Latitude(Float.valueOf(child3.getTextContent()));
         					else  if(child3.getNodeName().equals("content"))
         						data.setcontent(child3.getTextContent());
         					else if(child3.getNodeName().equals("picpath"))
         						data.setpicpath(child3.getTextContent());
         					else if(child3.getNodeName().equals("goodop"))
         						data.setgoodop(Integer.parseInt(child3.getTextContent()));
         					else if(child3.getNodeName().equals("badop"))
         						data.setbadop(Integer.parseInt(child3.getTextContent()));
         				}
         			} 
         			list.add(data);
         		}	
         	 } 
      }
         
   
		return list;
      }
      /* <id></id>   
       <addr_Longitude></addr_Longitude>
	   <addr_Latitude></addr_Latitude>
	   <addr></addr>
	   <content></content>
	   <picpath></picpath>
	   <goodop></goodop>
	   <badop></badop>
	   */
public Document parse(InputStream is) {   
    Document document = null;   
    try {   
       //DOM parser instance   
       DocumentBuilder builder = builderFactory.newDocumentBuilder();   
       //parse an XML file into a DOM tree   
       document = builder.parse(is);    
    } catch (ParserConfigurationException e) {   
  	  System.out.println(e.getMessage());
    } catch (SAXException e) {   
  	  System.out.println(e.getMessage());
    } catch (IOException e) {   
  	  System.out.println(e.getMessage());
    }   
    return document;   
 }   
}
