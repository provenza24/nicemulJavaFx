package nicemul.business.repository;

import java.util.List;

import nicemul.business.model.Console;
import nicemul.business.model.Rom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConsoleRepository extends JpaRepository<Console, Long> {

	public Console findByName(String name);
	
	@Query("select console.roms from Console console where console.name=?1")
	public List<Rom> findRoms(String consoleName);
	
	@Query("select console.miniIcon from Console console where console.name=?1")
	public String findMiniIcon(String consoleName);
}
