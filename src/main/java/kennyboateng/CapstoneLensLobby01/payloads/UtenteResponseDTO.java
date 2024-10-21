package kennyboateng.CapstoneLensLobby01.payloads;

public record UtenteResponseDTO(
        Long id,  // L'ID dell'utente
        String username, // Opzionale: nome utente
        String email // Opzionale: email dell'utente
) {
    public UtenteResponseDTO(Long id) {
        this(id, null, null);
    }

    public UtenteResponseDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}