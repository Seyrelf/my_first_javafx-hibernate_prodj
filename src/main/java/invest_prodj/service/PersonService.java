package invest_prodj.service;

import invest_prodj.model.Investment;
import invest_prodj.model.Person;
import invest_prodj.repository.PersonDao;
import java.util.ArrayList;
import java.util.List;

public class PersonService {

    private PersonDao personDao = new PersonDao();

    public PersonService(){}

    public Person findPerson(int id){
        return personDao.findById(id);
    }

    public void savePerson(Person person){
        personDao.save(person);
    }

    public void deletePerson(Person person){
        personDao.delete(person);
    }

    public void updatePerson(Person person){
        personDao.update(person);
    }

    public List<Person> findAllPersons(){
        return personDao.findAll();
    }

    public Person findPersonForCombobox(int id){
        List<Investment> people = new ArrayList<Investment>();
        Person person = personDao.findById(id);
        person.setNote("");
        person.setInvestments(people);
        person.setPhone_number("");
        System.out.println(person.toString());
        return person;
    }


}
