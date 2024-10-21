package kennyboateng.CapstoneLensLobby01.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UtentiPayloadDTO(
        @NotEmpty(message = "Devi inserire uno username")
        @Size(min = 3, max = 20, message = "Lo username deve avere dai 3 ai 20 caratteri")
        String username,

        @NotEmpty(message = "Devi inserire una email")
        @Email(message = "Devi inserire una email valida")
        String email,

        @NotEmpty(message = "Devi inserire una password")
        @Size(min = 8, message = "La password deve avere almeno 8 caratteri")
        String password,

        @NotEmpty(message = "Devi inserire un nome")
        @Size(min = 3, max = 20, message = "Il nome deve avere dai 3 ai 20 caratteri")
        String nome,

        @NotEmpty(message = "Devi inserire un cognome")
        @Size(min = 3, max = 40, message = "Il cognome deve avere dai 3 ai 40 caratteri")
        String cognome,

        String avatar
) {
}
