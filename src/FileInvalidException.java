
public class FileInvalidException extends Exception{

	FileInvalidException(String s)
	{
	 super(s);	
	}
	
	FileInvalidException()
	{
	 super("Error!! Input File cannot be parsed due to missing information");	
	}
	
	
	public String getMessage()
	{
	  return super.getMessage();	
	}
	
	
}
