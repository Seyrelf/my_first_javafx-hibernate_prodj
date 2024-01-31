package invest_prodj.service;

import invest_prodj.model.Investment;
import invest_prodj.repository.InvestmentDao;
import java.util.List;

public class InvestmentService {

    private InvestmentDao investmentDao = new InvestmentDao();

    public InvestmentService(){}

    public Investment findInvestment(int id){
        return investmentDao.findById(id);
    }

    public void saveInvestment(Investment investment){
        investmentDao.save(investment);
    }

    public void deleteInvestment(Investment investment){
        investmentDao.delete(investment);
    }

    public void updateInvestment(Investment investment){
        investmentDao.update(investment);
    }

    public String amount_sum(){
        return investmentDao.amount_sum().get(0).toString();}

    public String amount_by_person(int id_p){
        return investmentDao.amount_by_person(id_p).get(0).toString();}

    public List<Investment> findAllInvestments(){
        return investmentDao.findAll();
    }

    public List<Investment> findInvestmentByIdP(int id_p){return  investmentDao.findInvestmentByIdP(id_p);}

}
