package com.solutions.journalapp.service;

import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.solutions.journalapp.entity.JournalEntry;
import com.solutions.journalapp.entity.User;
import com.solutions.journalapp.repository.JournalEntryRepository;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntity(JournalEntry journalEntry,String userName){
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        User user = userService.findByUserName(userName);
        user.getJournalEntries().add(saved);
        userService.save(user);
    }
    public void saveEntity(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    public boolean deleteById(ObjectId id,String userName){
        User user = userService.findByUserName(userName);
        if(user == null){
            return false;
        }
        boolean removed = user.getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(id));
        if(!removed){
            return false;
        }
        userService.save(user);
        journalEntryRepository.deleteById(id);
        return true;
    }
    public void deleteAll(){
        journalEntryRepository.deleteAll();
    }
}
