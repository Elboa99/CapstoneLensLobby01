package kennyboateng.CapstoneLensLobby01.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record FotografiPayloadDTO( String username,
                                   String email,
                                   String password,
                                   String nome,
                                   String cognome,
                                   String biografia) {
}
