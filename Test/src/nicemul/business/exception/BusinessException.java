package nicemul.business.exception;

public class BusinessException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public BusinessException(String message)  {
		super(message);
	}
	
	public BusinessException(String message, Throwable t)  {
		super(message, t);
	}
	
	
	
}
