package nicemul;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import nicemul.business.exception.BusinessException;
import nicemul.business.service.console.IConsoleService;
import nicemul.business.util.ApplicationContextHolder;
import nicemul.ui.frame.MainFrame;

public class Launcher {

	public static void main(String[] args) throws BusinessException {
		
		ApplicationContextHolder.setXmlAppContext(new ClassPathXmlApplicationContext(new String[] {
				"spring/application-context-spring.xml"
				,"spring/jpa-context-spring.xml"}));
		
		IConsoleService consoleService = (IConsoleService) ApplicationContextHolder.getContext().getBean("consoleService");
		
		consoleService.scanConsoles();							
		consoleService.scanAllRoms();
		
		MainFrame mainFrame = new MainFrame();
		mainFrame.start(args);
	}

}