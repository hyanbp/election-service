package com.hyan.electionservice.api;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.TimeZone;

public class ElectionRequest {

    @NotEmpty
    private String name;
    @NotNull
    private TimeZone time;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimeZone getTime() {
        return time;
    }

    public void setTime(TimeZone time) {
        this.time = time;
    }
}
