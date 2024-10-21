package kennyboateng.CapstoneLensLobby01.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RecensioneDTO(

        String contenuto // Questo campo non è obbligatorio ora

) {
}