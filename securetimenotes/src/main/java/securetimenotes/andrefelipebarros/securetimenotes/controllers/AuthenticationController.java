package securetimenotes.andrefelipebarros.securetimenotes.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
