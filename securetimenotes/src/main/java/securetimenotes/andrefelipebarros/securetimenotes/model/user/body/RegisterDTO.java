package securetimenotes.andrefelipebarros.securetimenotes.model.user.body;

import securetimenotes.andrefelipebarros.securetimenotes.model.user.UserRole;

//Register Body:
public record RegisterDTO(String username, String password, UserRole role) {}
