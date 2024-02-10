package invest_prodj.service;

import invest_prodj.model.Diary;
import invest_prodj.repository.DiaryDao;

import java.util.List;

public class DiaryService {
    private DiaryDao diaryDao= new DiaryDao();

    public DiaryService(){}

    public Diary findDiary(int id){
        return diaryDao.findById(id);
    }

    public void saveDiary(Diary diary){
        diaryDao.save(diary);
    }

    public void deleteDiary(Diary diary){
        diaryDao.delete(diary);
    }

    public void updateDiary(Diary diary){
        diaryDao.update(diary);
    }

    public List<Diary> findAllDiary(){
        return diaryDao.findAll();
    }

    public Double sport_for_week_sum(){
        return diaryDao.sport_for_week_sum().get(0);}

    public Double learn_for_week_sum(){
        return diaryDao.learn_for_week_sum().get(0);}
}


