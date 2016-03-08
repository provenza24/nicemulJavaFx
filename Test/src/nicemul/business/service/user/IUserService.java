package nicemul.business.service.user;

import java.util.List;

import nicemul.business.exception.BusinessException;
import nicemul.business.model.User;


public interface IUserService {
		
	public List<User> findAll() throws BusinessException;
	
	public void save(User user) throws BusinessException;		
	
}
