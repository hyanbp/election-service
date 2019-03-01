package com.hyan.electionservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "elections")
public class Election {

    @Id
    private String id;
    private String name;
    private Integer expirationMinutes;
    private LocalDateTime openElection;
    private Integer yes;
    private Integer no;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExpirationMinutes() {
        return expirationMinutes;
    }

    public void setExpirationMinutes(Integer expirationMinutes) {
        this.expirationMinutes = expirationMinutes;
    }

    public LocalDateTime getOpenElection() {
        return openElection;
    }

    public void setOpenElection(LocalDateTime openElection) {
        this.openElection = openElection;
    }

    public Integer getYes() {
        return yes;
    }

    public void setYes(Integer yes) {
        this.yes = yes;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public void addYes() {
        this.yes = yes + 1;
    }

    public void addNo() {
        this.no = no + 1;
    }


    public static final class Builder {
        private String name;
        private Integer expirationMinutes;
        private LocalDateTime openElection;
        private Integer yes;
        private Integer no;


        private Builder() {
        }

        public static Builder of() {
            return new Builder();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder expirationMinutes(Integer expirationMinutes) {
            this.expirationMinutes = expirationMinutes;
            return this;
        }

        public Builder openElection(LocalDateTime openElection) {
            this.openElection = openElection;
            return this;
        }

        public Builder yes(Integer yes) {
            this.yes = yes;
            return this;
        }

        public Builder no(Integer no) {
            this.no = no;
            return this;
        }

        public Election build() {
            Election election = new Election();
            election.setName(name);
            election.setExpirationMinutes(expirationMinutes);
            election.setNo(no);
            election.setYes(yes);
            election.setOpenElection(openElection);
            return election;
        }
    }
}
