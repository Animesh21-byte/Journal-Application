package com.solutions.journalapp.entity;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "config_journal_app")
@Getter
@Setter
public class ConfigJournalAppEntity {
    private String key;
    private String value;
}
