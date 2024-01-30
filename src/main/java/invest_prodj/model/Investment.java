package invest_prodj.model;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
@Table(name = "investment")
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String name;
    private String note;
    private BigDecimal amount;
    private double percent;

    @Basic
    @Column(name = "person_id", nullable = true)
    private int person_id;

    @Basic
    @Column(name = "data", nullable = true)
    private Date data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id",insertable=false, updatable=false)
    private Person person;

    public Investment(){}

    public Investment(String name, String note, BigDecimal amount,double percent,int person_id,Date data) {
        this.name = name;
        this.note = note;
        this.amount = amount;
        this.percent = percent;
        this.person_id = person_id;
        this.data = data;
    }

    public Investment(String error){
        System.out.println(error);
    }


    @Override
    public String toString(){
        return ("Id: " + id + " name: "+ name + " note: " + note + " amount: " + amount);
    }
}