package nicemul.business.exception;

public class NoRollbackException extends BusinessException {
	
	private static final long serialVersionUID = 1L;
	
	public NoRollbackException(String message) {
		super(message);		
	}
	
	public NoRollbackException(String message, Throwable t) {
		super(message, t);	
	}	

}
