package com.solutions.journalapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.solutions.journalapp.entity.JournalEntry;
import com.solutions.journalapp.entity.User;
import com.solutions.journalapp.repository.UserRepository;
import com.solutions.journalapp.service.EmailService;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/send-email")
public class EmailController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @PostMapping("/{to}")
    public ResponseEntity<?> sendEmail(@PathVariable String to) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(auth.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        JournalEntry journal = user.getJournalEntries().stream().findFirst().orElse(null);
        if (journal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            emailService.sendEmail(to, journal.getTitle(), journal.getContent());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MailException e) {
            return new ResponseEntity<>("Email could not be sent", HttpStatus.BAD_GATEWAY);
        }
    }
    
}
