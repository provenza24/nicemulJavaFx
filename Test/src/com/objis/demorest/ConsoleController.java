package com.objis.demorest;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.ApplicationContext;

import com.objis.demorest.resource.ConsoleResource;

import nicemul.business.exception.BusinessException;
import nicemul.business.model.Console;
import nicemul.business.repository.ConsoleRepository;
import nicemul.business.service.console.IConsoleService;
import nicemul.business.util.ApplicationContextHolder;
 
@Path("/consoles")
public class ConsoleController {
 	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)	
	public List<ConsoleResource> getConsole() {
 		
		Properties props = System.getProperties();
		props.list(System.out);
		
		ApplicationContext ac = ApplicationContextHolder.getContext();
				
		try {
			IConsoleService consoleService = (IConsoleService) ApplicationContextHolder.getContext().getBean("consoleService");
			consoleService.scanConsoles();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		ConsoleRepository repo = ApplicationContextHolder.getContext().getBean(ConsoleRepository.class);
		
		List<Console> consoleList = repo.findAll();
		
		List<ConsoleResource> consoleResourceList = new ArrayList<>();
		
		for (Console console : consoleList) {
			ConsoleResource resource = new ConsoleResource();
			resource.mergeInto(console);
			consoleResourceList.add(resource);
		}
		
		return consoleResourceList;
				
	}
 
}
