package nicemul.ui.bridge;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.web.WebEngine;
import nicemul.business.exception.BusinessException;
import nicemul.business.service.console.IEmulatorService;
import nicemul.business.util.ApplicationContextHolder;
import nicemul.ui.PageEnum;
import nicemul.ui.bridge.page.HomePageBuilder;
import nicemul.ui.bridge.page.IPageBuilder;
import nicemul.ui.bridge.page.RomsPageBuilder;

public class UIBridge {

	private static Map<PageEnum, IPageBuilder> pageBuilders = new HashMap<PageEnum, IPageBuilder>();
	
	static {
		pageBuilders.put(PageEnum.HOME, new HomePageBuilder());
		pageBuilders.put(PageEnum.CONSOLE_ROMS, new RomsPageBuilder());
	}
	
	private WebEngine webEngine;

	public UIBridge(WebEngine pWebEngine) {
		webEngine = pWebEngine;
	}

	public void changePage(String page, String params) {

		try {			
			PageEnum pageEnum = PageEnum.valueOf(page);
			IPageBuilder pageBuilder = pageBuilders.get(pageEnum);
			String viewFile = viewFile = pageBuilder.buildPage(pageEnum.toLiteral(), params);
			
			webEngine.executeScript("document.getElementById('mainView').innerHTML = '" + viewFile + "';");
		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}
	
	public void launchRom(String romId) {
		
		IEmulatorService emulatorService = (IEmulatorService)ApplicationContextHolder.getContext().getBean("emulatorService");
		try {
			emulatorService.execute(Long.parseLong(romId));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
