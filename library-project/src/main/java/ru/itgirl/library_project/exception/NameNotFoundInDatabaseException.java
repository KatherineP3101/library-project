package ru.itgirl.library_project.exception;

public class NameNotFoundInDatabaseException extends Exception{

    public NameNotFoundInDatabaseException(String message) {
        super(message);
    }
}
