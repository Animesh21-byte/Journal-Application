package com.solutions.journalapp.controller;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
>>>>>>> 0e99347991aafaba034a4613f3645470f1bd616b
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.solutions.journalapp.entity.JournalEntry;
<<<<<<< HEAD
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
=======
import com.solutions.journalapp.service.JournalEntryService;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.ArrayList;
>>>>>>> 0e99347991aafaba034a4613f3645470f1bd616b
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
<<<<<<< HEAD
    private Map<Long,JournalEntry> journalEntry = new HashMap<>();
    
    @GetMapping()
    public List<JournalEntry> getAll(){
        return new ArrayList<JournalEntry>(journalEntry.values());
    }

    @GetMapping("id/{jid}")
    public JournalEntry getById(@PathVariable Long jid){
        return journalEntry.get(jid);
    }

    @GetMapping("/getbyparam")
    public JournalEntry get(@RequestParam long id) {
        return journalEntry.get(id);
    }
    
    @PostMapping()
    public void createEntry(@RequestBody JournalEntry param){
        journalEntry.put(param.getId(),param);
    }

    @PostMapping("/multiple")
    public void createEntryMltiple(@RequestBody ArrayList<JournalEntry> param){
        for(JournalEntry je : param){
            journalEntry.put(je.getId(),je);
        }
    }

    @DeleteMapping("/id/{id}")
    public JournalEntry deleteEntity(@PathVariable long id){
        return journalEntry.remove(id);
    }

    @PutMapping("/id/{id}")
    public void updateJournal(@PathVariable long id,@RequestBody JournalEntry param){
        journalEntry.put(id,param);
    }   
=======

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping()
    public List<JournalEntry> getAll(){
        return journalEntryService.getAll();
    }

    @GetMapping("id/{id}")
    public JournalEntry getById(@PathVariable ObjectId id) {
        return journalEntryService.getById(id).orElse(null);
    }

    @GetMapping("/getbyparam")
    public JournalEntry get(@RequestParam ObjectId id) {
        return journalEntryService.getById(id).orElse(null);
    }

    @PostMapping()
    public void createEntry(@RequestBody JournalEntry param) {
        param.setDate(LocalDateTime.now());
        journalEntryService.saveEntity(param);
    }

    @PostMapping("/multiple")
    public void createEntryMltiple(@RequestBody ArrayList<JournalEntry> param) {
        for (JournalEntry je : param) {
            je.setDate(LocalDateTime.now());
            journalEntryService.saveEntity(je);
        }
    }

    
    @DeleteMapping("/id/{id}")
    public void deleteEntity(@PathVariable ObjectId id){
        journalEntryService.deleteById(id);
    }
 
    @PutMapping("/id/{id}")
    public void updateJournal(@PathVariable ObjectId id,@RequestBody JournalEntry param){
        JournalEntry old = journalEntryService.getById(id).orElse(null);

        if(old != null){
            if(param.getTitle() != null && !param.getTitle().equals(old.getTitle())) old.setTitle(param.getTitle());
            if(param.getContent() != null && !param.getContent().equals(old.getContent())) old.setContent(param.getContent());
        }
        journalEntryService.saveEntity(old);
    }
    
>>>>>>> 0e99347991aafaba034a4613f3645470f1bd616b
}
