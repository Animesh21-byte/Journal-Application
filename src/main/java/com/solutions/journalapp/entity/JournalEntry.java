package com.solutions.journalapp.entity;
<<<<<<< HEAD

public class JournalEntry {

    private long id;
    private String title;
    private String content;
    public long getId() {
        return id;
    }
    public void setId(long id) {
=======
import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "journal_entries")
public class JournalEntry {

    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
>>>>>>> 0e99347991aafaba034a4613f3645470f1bd616b
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
<<<<<<< HEAD

=======
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
>>>>>>> 0e99347991aafaba034a4613f3645470f1bd616b
}
