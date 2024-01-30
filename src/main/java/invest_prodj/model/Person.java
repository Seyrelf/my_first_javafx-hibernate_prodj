package invest_prodj.model;
import lombok.Data;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String note;
    private String phone_number;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Investment> investments;



    public Person(String name, String note,String phone_number){
        this.name = name;
        this.phone_number = phone_number;
        this.note = note;
        investments = new ArrayList<Investment>();
    }

    public Person(String name,int id){
        this.name = name;
        this.id = id;
    }
    public Person(){}

    public void add_investment(Investment investment){
        investment.setPerson(this);
        investments.add(investment);
    }

    public void remove_investment(Investment investment){
        investments.remove(investment);
    }

}
