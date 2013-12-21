package TEST;

import java.util.ArrayList;

public class DATA {
	
	private static ArrayList<Message> list=new ArrayList();
	  
	  public static ArrayList getlist(){
		return list;
	   }
	   public void updatelist(Message[] data){
		   list.clear();
		   for(int i=0;i<data.length;i++){
			   list.add(data[i]);
		   }
	   }
	   public void clearlist(){
		   list.clear();
	   }
}
