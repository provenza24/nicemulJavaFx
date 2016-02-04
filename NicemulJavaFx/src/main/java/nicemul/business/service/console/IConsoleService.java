package nicemul.business.service.console;

import java.util.List;

import nicemul.business.exception.BusinessException;
import nicemul.business.model.Rom;


public interface IConsoleService {
	
	public void scanConsoles() throws BusinessException;
	
	public void scanAllRoms() throws BusinessException;
	 
	public List<Rom> findAllRoms() throws BusinessException;
	
	public List<Rom> findConsoleRoms(String consoleName) throws BusinessException;
	
	public void displayConsoles();
	
}
