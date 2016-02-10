package nicemul.ui.bridge.page;

import nicemul.business.exception.BusinessException;

public class HomePageBuilder extends AbstractPageBuilder {

	@Override
	public String updatePage(String viewFile, String params) throws BusinessException {		
		return viewFile;
	}

}
