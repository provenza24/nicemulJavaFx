package nicemul.business.service.console;

import nicemul.business.exception.BusinessException;
import nicemul.business.model.Rom;

public interface IRomService {

	public Rom findFullRom(long id) throws BusinessException;
}
