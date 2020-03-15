import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;




public class Driver {

  public static void main(String[] args)
  {
	  System.out.println("Welcome to Blibibliography Factory !!!");
	  processbegins();
  }
  
  
  
  
  
  public static void processbegins()
  {
	 String path="C:\\Users\\lenovo\\Desktop\\Files-249\\Latex"; 
	 
	 for (int i=1; i<=10; ++i)
	 {
	  
	    Scanner File=  existfile(path+i);
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
	    
	    if (out==null)
	     throw new FileNotFoundException();  	
	   }
	   
      catch(FileNotFoundException e)
	   {
    	  System.out.println("\nCannot Create "+name+" File!!!");
          System.exit(0);
	   }
	  
	   return out; 
	   
	   
   }
   
  
}
