package com.vacuum.app.whoisinspace.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 2017-08-01.
 */

public class Person {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
