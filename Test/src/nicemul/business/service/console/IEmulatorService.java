package nicemul.business.service.console;

import nicemul.business.exception.BusinessException;

public interface IEmulatorService {

	public void execute(long romId) throws BusinessException;
	
}
