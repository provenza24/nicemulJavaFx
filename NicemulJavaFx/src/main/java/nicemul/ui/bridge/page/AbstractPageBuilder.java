package nicemul.ui.bridge.page;

import nicemul.business.exception.BusinessException;
import nicemul.util.FileMsgGenerator;

public abstract class AbstractPageBuilder implements IPageBuilder {

	@Override
	public String buildPage(String page, String params) throws BusinessException {

		String viewFile = "";
		
		try {
			viewFile = FileMsgGenerator.generateStringFromFile("resources/html/" + page + ".html");
			viewFile = viewFile.replaceAll("\t", "");
			viewFile = viewFile.replaceAll("\r", "");
			viewFile = viewFile.replaceAll("\n", "");
			
			viewFile = updatePage(viewFile, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return viewFile;
	}

}
