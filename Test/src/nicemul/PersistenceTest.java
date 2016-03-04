package nicemul;

import nicemul.business.exception.BusinessException;
import nicemul.business.service.console.IConsoleService;
import nicemul.business.util.ApplicationContextHolder;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PersistenceTest {

	public static void main(String[] args) throws BusinessException {

		ApplicationContextHolder.setXmlAppContext(new ClassPathXmlApplicationContext(new String[] {
			"spring/application-context-spring.xml"
			,"spring/jpa-context-spring.xml"}));
		
		IConsoleService consoleService = (IConsoleService) ApplicationContextHolder.getContext().getBean("consoleService");
		consoleService.scanConsoles();							
		consoleService.scanAllRoms();
		consoleService.scanEmulators();
				
		consoleService.displayConsoles();
		
	}

}
