package com.solutions.journalapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.solutions.journalapp.entity.JournalEntry;
import com.solutions.journalapp.service.JournalEntryService;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

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
    
}
