package com.solutions.journalapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.Set;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<JournalEntry>> getAll(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getName().equalsIgnoreCase("Admin")){
            List<JournalEntry> journalEntryList = journalEntryService.getAll();
            if(journalEntryList!=null){
                return new ResponseEntity<>(journalEntryList,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping
    public ResponseEntity<List<JournalEntry>> getJernalEntriesOfUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(auth.getName());
        Set<JournalEntry> all = user.getJournalEntries();
        List<JournalEntry> journalEntryList = new ArrayList<>();
        for(JournalEntry journalEntry:all){
            journalEntryList.add(journalEntry);
        }
        if(!journalEntryList.isEmpty()){
            return new ResponseEntity<>(journalEntryList,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<JournalEntry> je = journalEntryService.getById(id);
        if(je.isPresent() && userService.findByUserName(auth.getName()).getJournalEntries().contains(je.get())){
            return new ResponseEntity<>(je.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry param){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            param.setDate(LocalDateTime.now());
            journalEntryService.saveEntity(param,auth.getName());
            return new ResponseEntity<>(param,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

     @PostMapping("/multiple")
    public ResponseEntity<?> createEntryMltiple(@RequestBody ArrayList<JournalEntry> param) {
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            for (JournalEntry journalEntry : param) {
                journalEntry.setDate(LocalDateTime.now());
                journalEntryService.saveEntity(journalEntry,auth.getName());
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllEntity(){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth.getName().equalsIgnoreCase("Admin")){
                journalEntryService.deleteAll();
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntity(@PathVariable String id){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(!ObjectId.isValid(id)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            boolean deleted = journalEntryService.deleteById(new ObjectId(id),auth.getName());
            if(deleted){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @PutMapping("{id}")
    public ResponseEntity<?> updateJournal(@PathVariable ObjectId id,@RequestBody JournalEntry param){
        JournalEntry old = journalEntryService.getById(id).orElse(null);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(userService.findByUserName(auth.getName()).getJournalEntries().contains(old)){
            if(old != null){
                if(param.getTitle() != null && !param.getTitle().equals(old.getTitle())) old.setTitle(param.getTitle());
                if(param.getContent() != null && !param.getContent().equals(old.getContent())) old.setContent(param.getContent());
                journalEntryService.saveEntity(old);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
}
