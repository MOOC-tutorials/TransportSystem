package model.exceptions;

public class DateNotFoundExpection extends Exception 
{
	private static final long serialVersionUID = 1L;
	
	public DateNotFoundExpection()
	{
		super("La fecha no es válida");
	}
}
