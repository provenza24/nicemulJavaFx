package nicemul.business.repository;

import nicemul.business.model.Emulator;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmulatorRepository extends JpaRepository<Emulator, Long> {

	public Emulator findByName(String name);	
}
