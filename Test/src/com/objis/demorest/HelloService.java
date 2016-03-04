package com.objis.demorest;
 
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
@Path("/hello")
public class HelloService {
 
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{param}")
	public List<Person> getMsg(@PathParam("param") String msg) {
 
		Person person = new Person("Franck", "Provenzano");
		Person person2 = new Person("Anas", "Dhouib");
		
		List<Person> personList = new ArrayList<Person>();
		personList.add(person);
		personList.add(person2);
		
		return personList;
				
	}
 
}
