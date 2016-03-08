package nicemul.rest.controller;
 
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import nicemul.business.exception.BusinessException;
import nicemul.business.model.User;
import nicemul.business.service.user.IUserService;
import nicemul.business.util.ApplicationContextHolder;
 
@Path("/users")
public class UserController {
 		
	private IUserService personService = ApplicationContextHolder.getContext().getBean(IUserService.class);
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)	
	public List<User> getUser() throws BusinessException {
 									
		List<User> personList = personService.findAll();		
		return personList;
				
	}	
	
	@POST
	public void post(@RequestBody User user) throws BusinessException {	    
				
		personService.save(user);			    
	}
	
 
}
