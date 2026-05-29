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
import com.solutions.journalapp.entity.User;
import com.solutions.journalapp.service.JournalEntryService;
import com.solutions.journalapp.service.UserService;
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
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<JournalEntry>> getAll(){
        List<JournalEntry> journalEntryList = journalEntryService.getAll();
        if(journalEntryList!=null){
            return new ResponseEntity<>(journalEntryList,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("{userName}")
    public ResponseEntity<List<JournalEntry>> getJernalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        List<JournalEntry> journalEntryList = new ArrayList<>();
        for(JournalEntry journalEntry:all){
            journalEntryList.add(journalEntry);
        }
        if(journalEntryList!=null && !journalEntryList.isEmpty()){
            return new ResponseEntity<>(journalEntryList,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId id) {
        Optional<JournalEntry> je = journalEntryService.getById(id);
        if(je.isPresent()){
            return new ResponseEntity<>(je.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getbyparam")
    public JournalEntry get(@RequestParam ObjectId id) {
        return journalEntryService.getById(id).orElse(null);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@PathVariable String userName,@RequestBody JournalEntry param){
        try{
            param.setDate(LocalDateTime.now());
            journalEntryService.saveEntity(param,userName);
            return new ResponseEntity<>(param,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

     @PostMapping("/multiple/{userName}")
    public ResponseEntity<?> createEntryMltiple(@PathVariable String userName, @RequestBody ArrayList<JournalEntry> param) {
        try{
            for (JournalEntry journalEntry : param) {
                journalEntry.setDate(LocalDateTime.now());
                journalEntryService.saveEntity(journalEntry,userName);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllEntity(){
        try{
            journalEntryService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{userName}/{id}")
    public ResponseEntity<?> deleteEntity(@PathVariable String id,@PathVariable String userName){
        try{
            if(!ObjectId.isValid(id)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            boolean deleted = journalEntryService.deleteById(new ObjectId(id),userName);
            if(deleted){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournal(@PathVariable ObjectId id,@RequestBody JournalEntry param){
        JournalEntry old = journalEntryService.getById(id).orElse(null);

        if(old != null){
            if(param.getTitle() != null && !param.getTitle().equals(old.getTitle())) old.setTitle(param.getTitle());
            if(param.getContent() != null && !param.getContent().equals(old.getContent())) old.setContent(param.getContent());
            journalEntryService.saveEntity(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
}
