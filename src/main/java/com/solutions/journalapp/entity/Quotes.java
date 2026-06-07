package com.solutions.journalapp.entity;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quotes {
    public String quote;
    public String author;
    public String work;
    public ArrayList<String> categories;
}