package nicemul.ui.bridge.menu;

import java.io.File;
import java.util.List;

import javafx.scene.web.WebEngine;
import nicemul.business.model.Rom;
import nicemul.business.service.console.IConsoleService;
import nicemul.business.util.ApplicationContextHolder;
import nicemul.util.FileMsgGenerator;
	
public class MenuBridge {
	
	private WebEngine webEngine;
	
	public MenuBridge(WebEngine pWebEngine) {
		webEngine = pWebEngine;
	}
	
    public void changePage(String page, String consoleName) {
    	    	
		try {
			String viewFile = FileMsgGenerator.generateStringFromFile("resources/html/"+page+".html");			
			viewFile = viewFile.replaceAll("\t", "");
			viewFile = viewFile.replaceAll("\r", "");
			viewFile = viewFile.replaceAll("\n", "");							
			
			String htmlRomList = "";
			IConsoleService consoleService = (IConsoleService) ApplicationContextHolder.getContext().getBean("consoleService");
			List<Rom> roms = consoleService.findConsoleRoms(consoleName);
			
			for (Rom rom : roms) {
				String gameLineFile = FileMsgGenerator.generateStringFromFile("resources/html/search_game_line.html");
				gameLineFile = gameLineFile.replaceAll("\t", "");
				gameLineFile = gameLineFile.replaceAll("\r", "");
				gameLineFile = gameLineFile.replaceAll("\n", "");
				gameLineFile = gameLineFile.replaceAll("%ROM_NAME%", rom.getFormatedName().replaceAll("'", "&rsquo;"));
				gameLineFile = gameLineFile.replaceAll("%CONSOLE_ICON%", rom.getConsole().getIcon());			
				gameLineFile = gameLineFile.replaceAll("%ROM_TYPE%", "");
				gameLineFile = gameLineFile.replaceAll("%ROM_DATE%", "");
				gameLineFile = gameLineFile.replaceAll("%CONSOLE_CLASS%", rom.getConsole().getName().replaceAll(" ", "_"));
				gameLineFile = gameLineFile.replaceAll("%FLAG_ICON%", "flag_eu.png");				
				
				
				String coverFile = rom.getConsole().getCoverFolder()+"/"+rom.getCoverName().replaceAll("'", "&rsquo;");				
				File file = new File(coverFile);
				if (file.exists()) {
					gameLineFile = gameLineFile.replaceAll("%ROM_COVER%", coverFile.replaceAll("resources", ".."));
				} else {
					gameLineFile = gameLineFile.replaceAll("%ROM_COVER%", "../covers/"+rom.getConsole().getDefaultRomCover());
				}
				
				htmlRomList = htmlRomList + gameLineFile;			
			}
			
			viewFile = viewFile.replaceAll("%ROM_LIST%", htmlRomList);
			
			webEngine.executeScript("document.getElementById('mainView').innerHTML = '" + viewFile + "';");					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
}
