package com.solutions.journalapp.entity;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;
import com.solutions.journalapp.enums.Sentiment;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "journal_entries")
@Getter
@Setter
//or can directly use @data
public class JournalEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;
}
