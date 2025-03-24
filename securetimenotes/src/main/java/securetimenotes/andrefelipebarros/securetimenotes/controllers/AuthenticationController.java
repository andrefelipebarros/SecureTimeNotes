package securetimenotes.andrefelipebarros.securetimenotes.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import securetimenotes.andrefelipebarros.securetimenotes.model.user.User;
import securetimenotes.andrefelipebarros.securetimenotes.model.user.UserRole;
import securetimenotes.andrefelipebarros.securetimenotes.model.user.body.AuthenticationDTO;
import securetimenotes.andrefelipebarros.securetimenotes.model.user.body.LoginResponseDTO;
import securetimenotes.andrefelipebarros.securetimenotes.model.user.body.RegisterDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import securetimenotes.andrefelipebarros.securetimenotes.infra.security.TokenService;
import securetimenotes.andrefelipebarros.securetimenotes.repository.UserRepository;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired 
    private UserRepository repository;

    @PostMapping("/register")
    public ResponseEntity<String> postMethodRegister(@RequestBody @Valid RegisterDTO data){

        if(repository.findByUsername(data.username()) != null) return ResponseEntity
        .badRequest().body("Username already taken.");

        String validPass = validatePassword(data.password());

        if (validPass == null) {
            System.out.println("Valid password!");
        } else{
            return ResponseEntity.badRequest().body("Password is invalid: must contain at least 8 characters, 1 uppercase letter, 1 number, and 1 special character");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserRole role = (data.role() == null) ? UserRole.USER : data.role();

        User newUser = new User(data.username(), encryptedPassword, role);
        this.repository.save(newUser);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> postMethodLogin(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User)auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }


    private String validatePassword(String password) {
        Map<String, String> rules = Map.of(
            ".*[A-Z].*", "one uppercase letter.",
            ".*[a-z].*", "one lowercase letter.",
            ".*\\d.*", "one digit",
            ".*[!@#$%^&*()].*", "one special character (!@#$%^&*())."
        );
    
        if (password == null || password.isEmpty())
            return "Password cannot be empty.";
        if (password.length() < 8)
            return "Password must be at least 8 characters long.";
    
        for (var entry : rules.entrySet()) {
            if (!password.matches(entry.getKey()))
                return "Password must contain at least " + entry.getValue();
        }
    
        return null;
    }
}
