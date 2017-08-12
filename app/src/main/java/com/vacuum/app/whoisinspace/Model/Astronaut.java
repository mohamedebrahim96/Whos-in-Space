package com.vacuum.app.whoisinspace.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Home on 2017-08-01.
 */

public class Astronaut {
    @SerializedName("people")
    private List<Person> people ;

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }
}
