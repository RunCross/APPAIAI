package edu.ahjzu.app.notice.server.xml;

public class usrXMLResolve {
	/*<usrs>
	 * <usr>
<id>2</id>
<name>hua</name>
<icon>usricon/001.jpg</icon>
<sex>2</sex>
<age>22</age>
<mode>1</mode>
<status>ffff</status>
<place>adf</place>
<latitude>162.33</latitude>
<longitiude>777555.33</longitiude>
<addr>erwe</addr>
<ip>192.168.1.101</ip>
</usr>
</usrs>*/
//	   @SuppressWarnings("null")
//	public static  ArrayList<UsrPro> getXMLData(InputStream is)  {
//		   ArrayList<UsrPro> usrs=null;
//		   UsrPro data=null;
//		   XmlPullParser parser=Xml.newPullParser();
//		   try {
//			parser.setInput(is, "utf-8");
//		} catch (XmlPullParserException e) {
//			// TODO Auto-generated catch block
//			System.out.println(e);
//		}
//		   int event = 0;
//		try {
//			event = parser.getEventType();
//		} catch (XmlPullParserException e) {
//			// TODO Auto-generated catch block
//			System.out.println(e);
//		}
//		   while(event!=XmlPullParser.END_DOCUMENT){
//			   switch(event){
//			   	case XmlPullParser.START_DOCUMENT:
//			   		
//			   		usrs=new  ArrayList<UsrPro>();
//				   break;
//			   	case XmlPullParser.START_TAG:
//			   		
//			   		if(parser.getName().equals("id")){
//			   			int id=0;
//			   			try {
//							id=new Integer(parser.nextText());
//							System.out.println("id:"+id);
//						} catch (NumberFormatException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						} catch (XmlPullParserException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						} catch (IOException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//			   				data=new UsrPro();
//							data.setId(id);
//			   			
//			   			}
//			   		if(parser.getName().equals("name")){
//						
//			   			try {
//							data.setName(parser.nextText());
//						} catch (XmlPullParserException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//			   			
//			   			}
//			   		if(parser.getName().equals("icon")){
//						
//			   			try {
//							data.setIcon(parser.nextText());
//						} catch (XmlPullParserException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//			   			}
//			   		if(parser.getName().equals("sex")){
//			   			
//			   			try {
//							data.setSex(new Integer(parser.nextText()));
//						} catch (NumberFormatException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (XmlPullParserException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//			   			
//			   			}
//			   		if(parser.getName().equals("age")){
//			   			
//			   			try {
//							data.setAge(new Integer(parser.nextText()));
//						} catch (NumberFormatException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (XmlPullParserException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//			   			
//			   			}
//			   		if(parser.getName().equals("mode")){
//			   			try {
//							data.setMode(new Integer(parser.nextText()));
//						} catch (NumberFormatException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (XmlPullParserException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//			   			
//			   			}
//			   		if(parser.getName().equals("status")){
//			   			try {
//							data.setStatus(parser.nextText());
//						} catch (XmlPullParserException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//			   			
//			   			}
//			   		if(parser.getName().equals("place")){
//			   			try {
//							data.setPlace(parser.nextText());
//						} catch (XmlPullParserException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//			   			
//			   			}
//			   		if(parser.getName().equals("latitude")){
//			   			try {
//							data.setLatitude(new Long(parser.nextText()));
//						} catch (NumberFormatException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (XmlPullParserException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//			   			
//			   			}
//			   		if(parser.getName().equals("longitiude")){
//			   			try {
//							data.setLongitiude(new Long(parser.nextText()));
//						} catch (NumberFormatException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (XmlPullParserException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//			   			
//			   			}
//			   		if(parser.getName().equals("addr")){
//			   			try {
//							data.setAddr(parser.nextText());
//						} catch (XmlPullParserException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//			   			
//			   			}
//			   		if(parser.getName().equals("ip")){
//			   			try {
//							data.setIp(parser.nextText());
//						} catch (XmlPullParserException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//			   			
//			   			}
//			   		
//			   			break;
//			   	case XmlPullParser.END_TAG:
//			   		if(parser.getName().equals("usr")){
//			   			usrs.add(data);
//			   			data=null;
//			   		}
//			   		break;
//			   		}
//			   
//			   try {
//				event=parser.next();
//			} catch (XmlPullParserException e) {
//				// TODO Auto-generated catch block
//				System.out.println(e);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				System.out.println(e);
//			}
//		   }
//		   
//		return usrs;
//		   
//	   }
	  /* 
	static DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance(); 
	
    
    public static ArrayList<usrData> getXMLData(InputStream in){
      ArrayList<usrData> list=new ArrayList<usrData>();
      Document document =parse(in);
   	  NodeList child1s = document.getChildNodes(); 
         for(int i=0;i<child1s.getLength();i++){
        	 Node child1 = child1s.item(i); 
        	 NodeList child2s = child1.getChildNodes(); 
        	 for (int j = 0; j < child2s.getLength(); j++) { 
        			Node child2 = child2s.item(j); 
        			if(child2.getNodeType()==Node.ELEMENT_NODE)
        			{
        			NodeList child3s = child2.getChildNodes();
        			usrData data=new usrData();
        			for (int k = 0; k < child3s.getLength(); k++) {
        				
        				Node child3=child3s.item(k);
        				if(child3.getNodeType()==Node.ELEMENT_NODE)
        				{
        					if(child3.getNodeName().equals("id"))
        						data.setId(Integer.parseInt(child3.getTextContent()));
        					else if(child3.getNodeName().equals("name"))
        						data.setName(child3.getTextContent());
        					else if(child3.getNodeName().equals("icon"))
        					    data.setIcon(child3.getTextContent());
        					else  if(child3.getNodeName().equals("sex"))
        						data.setSex(Integer.parseInt(child3.getTextContent()));
        					else if(child3.getNodeName().equals("age"))
        						data.setAge(Integer.parseInt(child3.getTextContent()));
        					else if(child3.getNodeName().equals("mode"))
        						data.setMode(Integer.parseInt(child3.getTextContent()));
        					else if(child3.getNodeName().equals("status"))
        						data.setStatus(child3.getTextContent());
        					else if(child3.getNodeName().equals("place"))
        						data.setPlace(child3.getTextContent());
        					else if(child3.getNodeName().equals("latitude"))
        						data.setLatitude(Double.parseDouble(child3.getTextContent()));
        					else if(child3.getNodeName().equals("longitiude"))
        						data.setLongitiude(Double.parseDouble(child3.getTextContent()));
        					else if(child3.getNodeName().equals("addr"))
        						data.setAddr(child3.getTextContent());
        				}
        			} 
        			list.add(data);
        		}	
        	 }
		}
		return list;
    	 
     }   /*<?xml version='1.0' encoding='UTF-8'?>
     * <usrs> 
     * 	<usr>
     * 		<id>1</id>
     * 		<name>zhao</name>
     * 		<icon>wu</icon>
     * 		<sex>1</sex>
     * 		<age>24</age>
     * 		<mode>1</mode>
     * 		<status>asfasf</status>
     * 		<place>afa</place>
     * 		<latitude>1636.22</latitude>
     * 		<longitiude>136.0</longitiude>
     * 		<addr>adfas</addr>
     * 	</usr>
     * </usrs>
     
     public static Document parse(InputStream is) {   
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
    	 }   */
}

 