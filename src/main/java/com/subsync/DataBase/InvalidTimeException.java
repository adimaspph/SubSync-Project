package com.subsync.DataBase;

public class InvalidTimeException extends Exception {
    public InvalidTimeException(){
        super("InvalidTimeException: Time cannot be minus");
    }
}
