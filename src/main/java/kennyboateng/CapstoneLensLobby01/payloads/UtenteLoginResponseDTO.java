package kennyboateng.CapstoneLensLobby01.payloads;

import kennyboateng.CapstoneLensLobby01.enums.Role;

public record UtenteLoginResponseDTO(String token,

                                     Role ruolo,

                                     Long utenteId) {
}
