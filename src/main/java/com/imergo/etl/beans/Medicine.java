package com.imergo.etl.beans;

/**
 * Created by korovin on 4/18/2017.
 */
public class Medicine {
    private String pzn;
    private String name;
    // TODO: add stoff

    public Medicine(String name, String pzn) {
        this.name = name;
        this.pzn = pzn;
    }

    public String getPzn() {
        return pzn;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "pzn='" + pzn + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
