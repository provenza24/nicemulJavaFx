package nicemul.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nicemul.business.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
}
