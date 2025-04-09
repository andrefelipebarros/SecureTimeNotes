package securetimenotes.andrefelipebarros.securetimenotes.model.user.body;

import io.swagger.v3.oas.annotations.media.Schema;
import securetimenotes.andrefelipebarros.securetimenotes.model.user.UserRole;

//Register Body:
public record RegisterDTO(
    @Schema(description = "Email válido do usuário")
    String username, 

    @Schema(description = "Senha com no mínimo 8 caracteres, 1 letra maiúscula, 1 número e 1 caractere especial")
    String password,

    @Schema(hidden = true)
    UserRole role
) {}