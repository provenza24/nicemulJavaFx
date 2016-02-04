package nicemul.business.exception;

public class RollbackException extends BusinessException {
	
	private static final long serialVersionUID = 1L;
	
	public RollbackException(String message) {
		super(message);		
	}
	
	public RollbackException(String message, Throwable t) {
		super(message, t);		
	}			
	
}
