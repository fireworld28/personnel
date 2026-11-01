package personnel;

public class DatesIncoherentes extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public DatesIncoherentes(String message)
	{
		super(message);
	}
}