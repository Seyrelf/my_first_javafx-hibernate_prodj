package invest_prodj.repository;

import invest_prodj.model.Diary;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.List;

public class DiaryDao {
    public Diary findById(int id){
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Diary diary = session.find(Diary.class,id);
        tx1.commit();
        session.close();
        return diary;
    }

    public void save(Diary diary){
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(diary);
        tx1.commit();
        session.close();
    }

    public void update(Diary diary){
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(diary);
        tx1.commit();
        session.close();
    }

    public void delete(Diary diary){
        Session session = HibernateSession.getSessionFactory().openSession();
        diary = (Diary) session.find(Diary.class,diary.getId());
        Transaction tx1 = session.beginTransaction();
        session.remove(diary);
        tx1.commit();
        session.close();
    }

    public List<Diary> findAll(){
        List<Diary> diary = (List<Diary>) HibernateSession.getSessionFactory().openSession().createQuery("From Diary").list();
        return diary;
    }

    public List<Double> sport_for_week_sum(){
        Session session = HibernateSession.getSessionFactory().openSession();
        LocalDate date = LocalDate.now();
        String hql = new String("select get_sport_time_for_week()");
        List<Double> sum = (List<Double>)session.createQuery(hql).list();
        return sum;}

    public List<Double> learn_for_week_sum(){
        Session session = HibernateSession.getSessionFactory().openSession();
        LocalDate date = LocalDate.now();
        String hql = new String("select get_learn_time_for_week()");
        List<Double> sum = (List<Double>)session.createQuery(hql).list();
        return sum;}
}
