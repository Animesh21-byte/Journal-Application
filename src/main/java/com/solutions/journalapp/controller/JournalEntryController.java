package com.solutions.journalapp.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.solutions.journalapp.entity.JournalEntry;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
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
}
