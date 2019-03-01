package com.hyan.electionservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "associates")
public class Associate {

    @Id
    private String id;
    private boolean alreadyVoted = Boolean.FALSE;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAlreadyVoted() {
        return alreadyVoted;
    }

    public void setAlreadyVoted(boolean alreadyVoted) {
        this.alreadyVoted = alreadyVoted;
    }
}
