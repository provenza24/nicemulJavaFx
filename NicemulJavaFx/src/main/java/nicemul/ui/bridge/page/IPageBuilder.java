package nicemul.ui.bridge.page;

import nicemul.business.exception.BusinessException;

public interface IPageBuilder {

	public String buildPage(String page, String params) throws BusinessException;
	
	public String updatePage(String page, String params) throws BusinessException;
	
}
