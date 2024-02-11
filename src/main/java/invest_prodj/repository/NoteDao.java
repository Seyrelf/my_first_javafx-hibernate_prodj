package invest_prodj.repository;

import invest_prodj.model.Note;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class NoteDao {
    public Note findById(int id){
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Note note = session.find(Note.class,id);
        tx1.commit();
        session.close();
        return note;
    }

    public void save(Note note){
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(note);
        tx1.commit();
        session.close();
    }

    public void update(Note note){
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(note);
        tx1.commit();
        session.close();
    }

    public void delete(Note note){
        Session session = HibernateSession.getSessionFactory().openSession();
        note = (Note) session.find(Note.class,note.getId());
        Transaction tx1 = session.beginTransaction();
        session.remove(note);
        tx1.commit();
        session.close();
    }

    public List<Note> findAll(){
        List<Note> note = (List<Note>) HibernateSession.getSessionFactory().openSession().createQuery("From Note").list();
        return note;
    }

}

