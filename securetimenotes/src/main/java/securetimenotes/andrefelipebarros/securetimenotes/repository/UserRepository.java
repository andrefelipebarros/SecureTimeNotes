package securetimenotes.andrefelipebarros.securetimenotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import securetimenotes.andrefelipebarros.securetimenotes.model.user.User;



public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByUsername(String username);
}
