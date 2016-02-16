package nicemul.ui.bridge.page;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import nicemul.business.exception.BusinessException;
import nicemul.business.model.Rom;
import nicemul.business.service.console.IConsoleService;
import nicemul.business.util.ApplicationContextHolder;
import nicemul.util.FileMsgGenerator;

public class RomsPageBuilder extends AbstractPageBuilder {

	@Override	
	public String updatePage(String pviewFile, String params) throws BusinessException {

		String viewFile = pviewFile;
		String consoleName = params;		
		IConsoleService consoleService = (IConsoleService) ApplicationContextHolder.getContext().getBean("consoleService");

		try {

			if (StringUtils.isNotBlank(consoleName)) {
				viewFile = viewFile.replaceAll("%CONSOLE_ICON%", consoleService.findConsoleMiniIcon(consoleName));
			}

			String htmlRomList = "";

			List<Rom> roms = consoleService.findFullConsoleRoms(consoleName);

			for (Rom rom : roms) {
				String gameLineFile = FileMsgGenerator.generateStringFromFile("resources/html/rom-line-detail-simple.html");
				gameLineFile = gameLineFile.replaceAll("\t", "");
				gameLineFile = gameLineFile.replaceAll("\r", "");
				gameLineFile = gameLineFile.replaceAll("\n", "");
				gameLineFile = gameLineFile.replaceAll("%ROM_NAME%", rom.getFormatedName().replaceAll("'", "&rsquo;"));
				gameLineFile = gameLineFile.replaceAll("%CONSOLE_ICON%", rom.getConsole().getIcon());
				gameLineFile = gameLineFile.replaceAll("%ROM_TYPE%", "");
				gameLineFile = gameLineFile.replaceAll("%ROM_DATE%", "");
				gameLineFile = gameLineFile.replaceAll("%CONSOLE_CLASS%", rom.getConsole().getName());
				gameLineFile = gameLineFile.replaceAll("%FLAG_ICON%", "flag_eu.png");
				gameLineFile = gameLineFile.replaceAll("%ROM_ID%", Long.toString(rom.getId()));				

				String coverFile = rom.getConsole().getCoverFolder() + "/" + rom.getCoverName().replaceAll("'", "&rsquo;");
				File file = new File(coverFile);
				if (file.exists()) {
					gameLineFile = gameLineFile.replaceAll("%ROM_COVER%", coverFile.replaceAll("resources", ".."));
				} else {
					gameLineFile = gameLineFile.replaceAll("%ROM_COVER%", "../covers/" + rom.getConsole().getDefaultRomCover());
				}

				htmlRomList = htmlRomList + gameLineFile;
			}

			viewFile = viewFile.replaceAll("%ROM_LIST%", htmlRomList);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return viewFile;
	}

}
