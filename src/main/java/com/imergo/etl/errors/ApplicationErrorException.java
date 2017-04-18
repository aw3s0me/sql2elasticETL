package com.imergo.etl.errors;

/**
 * Created by korovin on 4/18/2017.
 */
public class ApplicationErrorException extends RuntimeException {
    public ApplicationErrorException(String s) {
        super(s);
    }
}
