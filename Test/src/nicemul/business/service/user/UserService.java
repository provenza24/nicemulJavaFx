package nicemul.business.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nicemul.business.exception.BusinessException;
import nicemul.business.model.User;
import nicemul.business.repository.UserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository repo;

	public UserService() {
	}

	@Override
	public List<User> findAll() throws BusinessException {			
		return repo.findAll();
	}

	@Override
	public void save(User user) throws BusinessException {
	
		if (user.getId()!=0) {
			User dbUser = repo.findOne(user.getId());
			dbUser.merge(user);
			repo.save(dbUser);
		} else {
			repo.save(user);
		}
		
	}

}
