package in.codingstreams.etuserauthservice.data.repo;

import in.codingstreams.etuserauthservice.data.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser,Integer> {
    boolean existsByEmail(String email);
}
