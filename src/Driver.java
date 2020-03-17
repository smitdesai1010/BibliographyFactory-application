import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Driver {
	
	static HashMap<String,String> map=new HashMap<String,String>(); 
    static int number;
	
  public static void main(String[] args)
  {
	  System.out.println("Welcome to Blibibliography Factory !!!\n");
	  processbegins();
	 
  }
  
  
  
  
  
  public static void processbegins()
  {
	 String path="C:\\Users\\lenovo\\Desktop\\Files-249\\Latex"; 
	 intializemap();
	 
	 for (int i=1; i<=1; ++i)
	 {
	    number=0;
	    Scanner File=  existfile(path+i);
	    
	    if (!processFileForValidation(File))
	    {
	     System.out.println("The File Latex"+i+" is Invalid");	
	     System.exit(0);
	    }
	    
	    PrintWriter iee=createfiles(path,"IEEE"+i+".json");
	    PrintWriter acm=createfiles(path,"ACM"+i+".json");
	    PrintWriter nj=createfiles(path,"NJ"+i+".json");


	 }		 
	  
  }
  
  
  
  
  
   public static Scanner existfile(String path)
   {
	   path+=".bib";
	   
	   Scanner file=null;
	   
	   try
	   {
	    file=new Scanner(new FileInputStream(path));
	    
	    if (file==null)
	     throw new FileNotFoundException();  	
	   }
	   
      catch(FileNotFoundException e)
	   {
    	  System.out.println("\nCould not open input file "+path.substring(34)+" for reading. Please check if file exists! Program will terminate after closing any opened files");
          System.exit(0);
	   }
	  
	   return file;
   }
   
   
   public static PrintWriter createfiles(String path,String name)
   {
	   PrintWriter out=null;
	   
	   try
	   {
	    out=new PrintWriter(new FileOutputStream(path.substring(0,34)+"\\Files\\"+name));    
	     //throw new FileNotFoundException();  	
	   }
	   
      catch(FileNotFoundException e)
	   {
    	  System.out.println("\nCannot Create "+name+" File!!!");
          System.exit(0);
	   }
	  
	   return out; 
	   
	   
   }
   
   
   public static boolean processFileForValidation(Scanner file)
   {   
	  String info=convertintostring(file);
              	
        /* 
         if (!s.equals(""))
         {
        	
        	if (s.indexOf("=")==-1)
        	 continue;
        	
          info=s.substring(s.indexOf("=")+1).trim();
          System.out.println(info);
         } 
         
          //if (info.equals(""))
           //return false;	 
          */
        // System.out.println(info);
         
         String[] parts=converttoparts(info);
	    
         
         for (int i=0; i<parts.length; ++i)
         {
           map.put("author", parts[i].substring(parts[i].indexOf("author=")+7,parts[i].indexOf("~", parts[i].indexOf("author=")))  ); 
           map.put("journal", parts[i].substring(parts[i].indexOf("journal=")+8,parts[i].indexOf("~", parts[i].indexOf("journal=")))  ); 
           map.put("title", parts[i].substring(parts[i].indexOf("title=")+6,parts[i].indexOf("~", parts[i].indexOf("title=")))  ); 
           map.put("year", parts[i].substring(parts[i].indexOf("year=")+5,parts[i].indexOf("~", parts[i].indexOf("year=")))  ); 
           map.put("volume", parts[i].substring(parts[i].indexOf("volume=")+7,parts[i].indexOf("~", parts[i].indexOf("volume=")))  ); 
           map.put("number", parts[i].substring(parts[i].indexOf("number=")+7,parts[i].indexOf("~", parts[i].indexOf("number=")))  ); 
           map.put("pages", parts[i].substring(parts[i].indexOf("pages=")+6,parts[i].indexOf("~", parts[i].indexOf("pages=")))  ); 
           map.put("doi", parts[i].substring(parts[i].indexOf("doi=")+4,parts[i].indexOf("~", parts[i].indexOf("doi=")))  ); 
           map.put("month", parts[i].substring(parts[i].indexOf("month=")+6,parts[i].indexOf("~", parts[i].indexOf("month=")))  ); 
           
           
           for (Map.Entry<String, String> entry : map.entrySet()) {
        	
        	 if (entry.getValue().equals(""))
              return false;   
        	   
   		    System.out.println(entry.getKey() + " = " + entry.getValue());
           }
           
           System.out.println();
         }	 
	  
	  return true;
   }
   
   
   
   public static String convertintostring(Scanner file)
   {
	   
	      String s=new String();
		  String info=new String();
		        
		  while (file.hasNextLine())
		  { 
				  
			 s=file.nextLine().trim();
			 
	        if (!datacleaning(s).equals(""))
	         info=info+"\n"+datacleaning(s);
	        
		  }
	       
	   return info;
   }
   
   
   
   
    public static String datacleaning(String s)
    {
    	 s=s.replace(",", "");
		  
		 if (s.equals(""))
		  return "";
		  
		 if (s.equals("@ARTICLE{"))
		 {
			 ++number;
			 return "";
		 }
		 
		 if (s.equals("}"))
		  return "^";
		 
		 s=s.replace("{","");
		 s=s.replace("}","~");
		 
		 if (s.length()>=8 && s.substring(0,8).equals("keywords"))
		  s="";
		 
		 if (s.length()>=4 && s.substring(0,4).equals("ISSN"))
		  s="";
		 
		 try 
		 {
		        double d = Double.parseDouble(s);
		        s="";
		 } 

		 catch (NumberFormatException nfe) 
		 {}
    	
    	
      return s;
    }
    
    
    
    
    public static String[] converttoparts(String info)
    {
    	String[] parts=new String[number];
    	int index=0;
    	int start=0;
    	int end=0;
    	
       while (index < number)
       {
    	 start=end+1;
    	 end=info.indexOf("^",start+1);
    	 
    	 parts[index]=info.substring(start,end);
     	 ++index;	 
       }	
    	
        for (int i=0; i<parts.length; ++i)
    	System.out.println(parts[i]);
       
    	return parts;
    }
    
    

    public static void intializemap()
    {
      map.put("author","");
      map.put("journal","");
      map.put("title","");
      map.put("year","");
      map.put("volume","");
      map.put("number","");
      map.put("pages","");
      map.put("doi","");
      map.put("month","");
    }
}