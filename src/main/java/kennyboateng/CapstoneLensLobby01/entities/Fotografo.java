package kennyboateng.CapstoneLensLobby01.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fotografi")
public class Fotografo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column
    private String biografia;


    @OneToMany(mappedBy = "fotografo", cascade = CascadeType.ALL)
    private List<Immagine> immagini;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "utente_id", referencedColumnName = "id")
    private Utente utente;


    public Fotografo(Utente utente, String biografia) {
        this.utente = utente;
        this.biografia = biografia;
    }
}


