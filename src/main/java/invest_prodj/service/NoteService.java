package invest_prodj.service;

import invest_prodj.model.Note;
import invest_prodj.repository.NoteDao;
import java.util.List;

public class NoteService {
    private NoteDao noteDao= new NoteDao();

    public NoteService(){}

    public Note findNote(int id){
        return noteDao.findById(id);
    }

    public void saveNote(Note note){
        noteDao.save(note);
    }

    public void deleteNote(Note note){
        noteDao.delete(note);
    }

    public void updateNote(Note note){
        noteDao.update(note);
    }

    public List<Note> findAllNote(){
        return noteDao.findAll();
    }

}