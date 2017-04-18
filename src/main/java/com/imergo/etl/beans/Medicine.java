package com.imergo.etl.beans;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by korovin on 4/18/2017.
 */
public class Medicine {
    private String pzn;
    private String name;
    private ArrayList<String> stoff;

    public Medicine(String name, String pzn, String stoff) {
        this.name = name;
        this.pzn = pzn;
        this.stoff = new ArrayList<>(Arrays.asList(stoff.split(",")));
    }

    public String getPzn() {
        return pzn;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getStoff() {
        return stoff;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "pzn='" + pzn + '\'' +
                ", name='" + name + '\'' +
                ", stoff=" + stoff +
                '}';
    }
}
