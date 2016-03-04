package com.objis.demorest;
 
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nicemul.business.exception.BusinessException;
import nicemul.business.model.Console;
import nicemul.business.model.Rom;
import nicemul.business.repository.ConsoleRepository;
import nicemul.business.service.console.IConsoleService;
import nicemul.business.util.ApplicationContextHolder;
import nicemul.business.util.Folders;
 
@Path("/consoles")
public class ConsoleController {
 		
	private ConsoleRepository repo = ApplicationContextHolder.getContext().getBean(ConsoleRepository.class);
	
	private IConsoleService consoleService = ApplicationContextHolder.getContext().getBean(IConsoleService.class);
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Console> getConsole() {
 				
		/*ApplicationContext ac = ApplicationContextHolder.getContext();
				
		try {
			IConsoleService consoleService = (IConsoleService) ApplicationContextHolder.getContext().getBean("consoleService");
			consoleService.scanConsoles();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/		
						
		List<Console> consoleList = repo.findAll();
		
		return consoleList;
				
	}
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/roms")
	public List<Rom> getConsoleRoms(@PathParam("id") String name) {
 						
		Console console = repo.findByName(name);
		
		List<Rom> romList = new ArrayList<>();
		try {			
			romList = consoleService.findConsoleRoms(name);
			for (Rom rom : romList) {
				rom.setCoverName(console.getCoverFolder() + File.separatorChar + rom.getCoverName());
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return romList;
				
	}
	
	
 
}
