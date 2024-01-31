package invest_prodj.repository;

import invest_prodj.model.Investment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.math.BigDecimal;
import java.util.List;

public class InvestmentDao {

    public Investment findById(int id){
        return HibernateSession.getSessionFactory().openSession().get(Investment.class,id);
    }

    public void save(Investment investment){
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(investment);
        tx1.commit();
        session.close();
    }

    public void update(Investment investment){
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(investment);
        tx1.commit();
        session.close();
    }

    public void delete(Investment investment){
        Session session = HibernateSession.getSessionFactory().openSession();
        investment = (Investment) session.find(Investment.class,investment.getId());
        Transaction tx1 = session.beginTransaction();
        session.remove(investment);
        tx1.commit();
        session.close();
    }

    public List<BigDecimal> amount_sum(){
        Session session = HibernateSession.getSessionFactory().openSession();
        String hql = "Select sum(amount) from Investment";
        List<BigDecimal> sum = (List<BigDecimal>)session.createQuery(hql).list();
        return sum;}

    public List<BigDecimal> amount_by_person(int id_p){
        Session session = HibernateSession.getSessionFactory().openSession();
        String hql = "Select sum(amount) from Investment Where person_id="+id_p;
        List<BigDecimal> sum = (List<BigDecimal>)session.createQuery(hql).list();
        return sum;}

    public List<Investment> findInvestmentByIdP(int id_p){
        List<Investment> investments = (List<Investment>) HibernateSession.getSessionFactory().openSession().createQuery("From Investment where person_id =" + id_p).list();
        return investments;
    }

    public List<Investment> findAll(){
        List<Investment> investments = (List<Investment>) HibernateSession.getSessionFactory().openSession().createQuery("From Investment").list();
        return investments;
    }
}
