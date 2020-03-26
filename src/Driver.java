import java.util.Scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;


public class Driver {

	
	/**
	 * Used a hashmap to map and store all objects and their values in the json file
	 * Also used it to minimise the opening and reading of a file. 
	 */
	
	static HashMap<String,String> map=new HashMap<String,String>(); 
    static int number;
	
    
    
    /**
     * The main is divided into 2 parts
     * 
     * processbegins open each files, creates corresponding output files if it is valid and then writes them up.
     * 
     * tryopeningafile as name suggested, try's opening a file if it exists and then displays it content. 
     * 
     * @param args
     */
    
  public static void main(String[] args)
  {
	  System.out.println("Welcome to Blibibliography Factory !!!\n");
	  processbegins();
	  
	  System.out.println("\n");
	  
	  tryopeningafile();
  }
  
  
  
  /**
   * iterates through the 10 files,each at a time and then calls the function processFileForValidation.
   */
  
  
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
  
  
  /**
   * 
   * This method takes a filepath and if the filepath exits then it opens it using the Scanner stream and returns the scanner object with the 
   * stream associated with it.
   * 
   * @param path: The filepath in the system
   * @return Scanner object either null or with the input-stream. 
   */
  
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
   
   
  /**
   * Creates a file in the specified directory
   * If un-successful, the program gets terminated
   * 
   * @param path : Directory in which the file is to created
   * @param name: The name of the file tobe created
   * @return a printwriter object either null or a stream associated with the successfully created file
   */
   
   public static PrintWriter createfiles(String path,String name)
   {
	   PrintWriter out=null;
	   
	   try
	   {
	    out=new PrintWriter(new FileOutputStream(path.substring(0,34)+"Files\\"+name));      	
	   }
	   
      catch(FileNotFoundException e)
	   {
    	  System.out.println("\nCannot Create "+name+" File!!!");
          System.exit(0);
	   }
	  
	   return out;   
   }
   
   
   /**
    * 
    * First it takes the file and converts it into a string by using the convertintostring method to minimize the working on the file later on.
    * Then it converts that String(Containing all information about the file) into a array of string each containing one article block.
    * 
    * Then it creates 3 files(ieee,acm and nj) using the createfile method.
    * 
    * Then it iterates through the array of String and maps each field in a article block to its value using a HashMap.
    * 
    * So, till here we have copied the content of file into a string, then the string into a array of string each containing one block (i.e Article)
    * Then, it takes every block each at a time and breaks the block into the fields and its values and maps them using a HashMap
    * 
    * Now, it will iterate over the current state of the HashMap (After we have mapped each field to its value), and check if a particular value 
    * is empty of not.
    * If empty it throws a FileInvalidException.
    * If not,then it sends them for writing it into the ieee, acm and nj files.
    * 
    *  
    *  Finally, the methodd closes the streams associated with the ieee, acm and nj files.
    * 
    * @param file: A Scanner stream associated with a Latex file
    * @param index: The current Latex file (i.e Latex2 or Latex9)
    * @param path: Directory path of the path
    */
   
   
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
    	    writeacm(acm,i+1);
    	    writenj(nj);
           }  
         }
         
         
  	      ieee.close();
		  acm.close();
		  nj.close();

   }
   
   
   
   
   /**
    * Writes one article in the corresponding IEEE file to the printwriterstream passed
    * It takes the information from the HashMap and modifies it to the IEEE format.
    *  
    * @param ieee: A printwriter object with a FileOutputStream associated with it.
    */
   
   public static void writeieee(PrintWriter ieee)
   {
	   ieee.print(map.get("author").replace("and", ",")+". ");
	   ieee.print(" \""+map.get("title") + "\", " + map.get("journal")+ ", vol." + map.get("volume") + ", no." + map.get("number")+ ", p."+map.get("pages"));
	   ieee.println(", "+map.get("month")+" "+map.get("year")+"."+"\n");
	   
   }
   
   
   /**
    * Writes one article in the corresponding ACM file to the printwriterstream passed
    * It takes the information from the HashMap and modifies it to the ACM format.
    *  
    * @param acm: A printwriter object with a FileOutputStream associated with it.
    * @param i: The current index (i.e [1] or [2] or [3]....)
    */
   
   public static void writeacm(PrintWriter acm,int i)
   {
	   int n=map.get("author").indexOf("and");
	   
	   n= n==-1 ? map.get("author").length() : n;
	   
	   acm.print("["+i+"]"+"\t");
	   acm.print(map.get("author").substring(0,n) + "et all. " );
	   acm.print(map.get("year")+". "+ map.get("title") + ". " + map.get("journal")+ ". " + map.get("volume") +", "+ map.get("number")+"("+map.get("year")+")" );
	   acm.println(", "+map.get("pages")+". "+"DOI: https://doi.org/"+map.get("doi")+"."+"\n");
   }
   
   
   /**
    * Writes one article in the corresponding NJ file to the printwriterstream passed
    * It takes the information from the HashMap and modifies it to the NJ format.
    *  
    * @param nj: A printwriter object with a FileOutputStream associated with it.
    */
   
   public static void writenj(PrintWriter nj)
   {
	   nj.print(map.get("author").replace("and", "&")+". ");
	   nj.print(map.get("title") + ". " + map.get("journal")+ ". " + map.get("volume") + ", "+map.get("pages"));
       nj.println("("+map.get("year")+")" +"."+ "\n");
   }
   
   
   
   /**
    * This method iterates through the Latex file via the Scanner stream passed on.
    * Then sends each line for data-cleaning , if the string after datacleaning is not empty , it adds it to the string info
    * 
    * @param file: A Scanner stream asssociated with a Latex file.
    * @return info: a string with all cleaned content of the file
    */
   
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
   
   
   /**
    * It takes a line and cleans it.
    * 
    * Meaning of Cleaning: removes unnecssary characters like ',' , '@ARTICLE' , '{' , '}' , certain fields like 'keywords' , 'ISSN' and the number
    *                      Also it replaces '}' with special characters that serve as a delimiter character 
    *                        
    * @param s: A line from the latex file.
    * @return s: the same parameter after cleaning it.
    */
   
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
		 
		 try        // try's parsing the line to a double , if its a number it will be successful otherwise it will continue.
		 {
		       double d = Double.parseDouble(s);
		       s="";
		 } 

		 catch (NumberFormatException nfe) 
		 {}
    	
    	
      return s;
    }
    
    
    
    /**
     * 
     * @param info : A string containing all the cleaned data from a file.
     * @return parts: An array of String with each element containing a Article block.
     */
    
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
    	
    	return parts;
    }
    
    
    
    public static void tryopeningafile()
    {
    	
     BufferedReader read=null;
     String path="C:\\Users\\lenovo\\Desktop\\Files-249\\Files\\";
     Scanner keyboard=new Scanner(System.in);
     
      try
      {
    	System.out.print("Enter File name to be opened: ");
    	String filename=keyboard.next();
    	filename=path+filename+".json";
    	
    	read=new BufferedReader (new FileReader(filename));
    	
    	String line=read.readLine();
    	
    	while (line!=null)
    	{
    	 System.out.println(line);	
    	 line=read.readLine();
    	}
    	
      }
      
      
      catch(FileNotFoundException e)
      {
    	 System.out.println("\n"+e.getMessage());
    	 System.out.println("\nYou will given one more chance");
    	 
    	 
    	  try
          {
        	System.out.print("Enter File name to be opened: ");
        	String filename=keyboard.next();
        	filename=path+filename+".json";
        	
        	read=new BufferedReader (new FileReader(filename));
        	
        	String line=read.readLine();
        	
        	while (line!=null)
        	{
        	 System.out.println(line);	
        	 line=read.readLine();
        	}
        	
          }
    	  
    	  
    	  catch(FileNotFoundException ex)
    	  {
    		 System.out.println(ex.getMessage()); 
    		 System.out.println("BYE");
    		 System.exit(0);
    	  }
    	  
    	  catch(IOException ex)
          {
        	  System.out.println(e.getMessage());
          }
    	  
      }
      
      catch(IOException e)
      {
    	  System.out.println(e.getMessage());
      }
      
      finally
      {
    	 try
    	 {
    	  read.close();
    	 } 
    	 
    	 catch(IOException e)
         {
       	  System.out.println(e.getMessage());
         }
    	 
    	  keyboard.close();
      }
      
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