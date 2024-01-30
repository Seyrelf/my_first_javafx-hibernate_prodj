package invest_prodj.repository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import invest_prodj.model.Investment;
import invest_prodj.model.Person;

import java.util.List;

public class PersonDao {

    public Person findById(int id){
        return HibernateSession.getSessionFactory().openSession().get(Person.class,id);
    }

    public void save(Person person){
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(person);
        tx1.commit();
        session.close();
    }

    public void update(Person person){
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(person);
        tx1.commit();
        session.close();
    }

    public void delete(Person person){
        Session session = HibernateSession.getSessionFactory().openSession();
        person = (Person) session.find(Person.class,person.getId());
        Transaction tx1 = session.beginTransaction();
        session.remove(person);
        tx1.commit();
        session.close();  //закрываем сессию
    }

    /*public void delete(Person person){
        Session session = HibernateSession.getSessionFactory().openSession().createQuery("Delete from Person where id = " + person.getId()).;
    }*/

    public List<Person> findAll(){
        List<Person> persons = (List<Person>) HibernateSession.getSessionFactory().openSession().createQuery("From Person").list();
        return persons;
    }
}
