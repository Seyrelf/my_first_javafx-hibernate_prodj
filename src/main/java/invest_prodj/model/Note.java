package invest_prodj.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    private String note;
    private String link;

    public Note(String note, String link) {
        this.note = note;
        this.link = link;
    }

    public Note(){}


    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
