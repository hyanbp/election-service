package com.hyan.electionservice.api.response;

public class ResultVoteResponse {

    Integer yes;

    Integer no;

    public ResultVoteResponse() {
    }

    public ResultVoteResponse(Integer yes, Integer no) {
        this.yes = yes;
        this.no = no;
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
}
