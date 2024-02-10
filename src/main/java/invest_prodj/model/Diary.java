package invest_prodj.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date date_create;
    private String note;
    private String sport_info;
    private String learn_info;
    private double sport_time;
    private double learn_time;

    public Diary(Date date_create, String note, String sport_info, String learn_info, double sport_time, double learn_time) {
        this.date_create = date_create;
        this.note = note;
        this.sport_info = sport_info;
        this.learn_info = learn_info;
        this.sport_time = sport_time;
        this.learn_time = learn_time;
    }

    public Diary() {
    }

    @Override
    public String toString() {
        return "Diary{" +
                "id=" + id +
                ", date_create=" + date_create +
                ", note='" + note + '\'' +
                ", sport_info='" + sport_info + '\'' +
                ", learn_info='" + learn_info + '\'' +
                ", sport_time=" + sport_time +
                ", learn_time=" + learn_time +
                '}';
    }
}

