import java.util.Scanner;
import java.io.File;
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
	 
	 for (int i=1; i<=10; ++i)
	 {
	    number=0;
	    Scanner File= existfile(path+i);
	    
	    processFileForValidation(File,i,path);
         
	    File.close();
	 }		 
	  
  }
  
  
  
   public static Scanner existfile(String path)
   {
	   path+=".bib";
	   
	   Scanner file=null;
	   
	   try
	   {
	    file=new Scanner(new FileInputStream(path)); 	
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
	    out=new PrintWriter(new FileOutputStream(path.substring(0,34)+"Files\\"+name,true));      	
	   }
	   
      catch(FileNotFoundException e)
	   {
    	  System.out.println("\nCannot Create "+name+" File!!!");
          System.exit(0);
	   }
	  
	   return out;   
   }
   
   
   public static void processFileForValidation(Scanner file,int index,String path)
   {   
	     String info=convertintostring(file);
   
         String[] parts=converttoparts(info);
	    
        PrintWriter ieee=createfiles(path,"IEEE"+index+".json");
  	    PrintWriter acm=createfiles(path,"ACM"+index+".json");
  	    PrintWriter nj=createfiles(path,"NJ"+index+".json");
         
         
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
           
           boolean valid=true;
           
           for (Map.Entry<String, String> entry : map.entrySet()) 
           {
        	 try
        	 {
        	  if (entry.getValue().equals(""))
        	   throw new FileInvalidException("The File Latex"+index+" is Invalid"+"\t Field "+entry.getKey()+" is empty");         	
        	 } 
        	 
        	 
        	 catch (FileInvalidException e)
        	 {
        		 valid=false; 
        		 System.out.println(e.getMessage());
        		 
        		 ieee.close();
        		 acm.close();
        		 nj.close();
        		 
        		 
        		 new File(path.substring(0,34)+"Files\\"+"IEEE"+index+".json").delete();
        		 new File(path.substring(0,34)+"Files\\"+"ACM"+index+".json").delete();
        		 new File(path.substring(0,34)+"Files\\"+"NJ"+index+".json").delete();
        		 
        		 break;
        	 }      	 
        	         	 
           } 
           
           if (valid)
           {
    	    writeieee(ieee);
    	    writeacm(acm);
    	    writenj(nj);
           }  
         }
         
         
  	      ieee.close();
		  acm.close();
		  nj.close();

   }
   
   
   
   
   public static void writeieee(PrintWriter ieee)
   {
	   ieee.print(map.get("author").replace("and", ",")+". ");
	   ieee.print(" \""+map.get("title") + "\", " + map.get("journal")+ ", vol." + map.get("volume") + ", no." + map.get("number")+ ", p."+map.get("pages"));
	   ieee.println(", "+map.get("month")+" "+map.get("year")+"."+"\n");
	   
	  // System.out.println("\n");
   }
   
   public static void writeacm(PrintWriter acm)
   {
	   acm.print(map.get("author").substring(0,map.get("author").indexOf("and")) + "et all. " );
	   acm.print(map.get("year")+". "+ map.get("title") + ". " + map.get("journal")+ ". " + map.get("volume") +", "+ map.get("number")+"("+map.get("year")+")" );
	   acm.println(", "+map.get("pages")+". "+"DOI: https://doi.org/"+map.get("doi")+"."+"\n");
   }
   
   
   public static void writenj(PrintWriter nj)
   {
	   nj.print(map.get("author").replace("and", "&")+". ");
	   nj.print(map.get("title") + ". " + map.get("journal")+ ". " + map.get("volume") + ", "+map.get("pages"));
       nj.println("("+map.get("year")+")" +"."+ "\n");
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
    	
        //for (int i=0; i<parts.length; ++i)
    	//System.out.println(parts[i]);
       
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