package invest_prodj.service;

import invest_prodj.model.Person;
import invest_prodj.repository.PersonDao;
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


}
