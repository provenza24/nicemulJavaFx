package nicemul.business.service.console;

import nicemul.business.exception.BusinessException;
import nicemul.business.model.Rom;
import nicemul.business.repository.RomRepository;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RomService implements IRomService{

	@Autowired
	private RomRepository romRepository;
	
	public Rom findFullRom(long id) throws BusinessException {
		Rom rom = romRepository.findOne(id);
		Hibernate.initialize(rom.getConsole().getEmulators());
		return rom;
	}
}
