package securetimenotes.andrefelipebarros.securetimenotes.model.user;

import securetimenotes.andrefelipebarros.securetimenotes.model.note.Note;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.Getter;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "users")
@Entity(name = "users")
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String password;
    private UserRole role;

    // Relacionamento OneToMany com Note
    @OneToMany(mappedBy = "user")
    private List<Note> notes;

    //Criar constructor com 3 parâmetros(retirando id):
    public User(String username, String password, UserRole role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) 
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), 
            new SimpleGrantedAuthority("ROLE_USER"));

        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    //METHODS UserDetails:
    @Override
    public String getUsername() {
        return username;
    }
    
    //Temporarily setting "true"
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
