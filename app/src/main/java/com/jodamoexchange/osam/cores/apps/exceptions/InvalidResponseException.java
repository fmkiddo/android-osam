package com.jodamoexchange.osam.cores.apps.exceptions;

public class InvalidResponseException extends Exception {

    public InvalidResponseException () {
        super();
    }

    public InvalidResponseException (Throwable cause) {
        super(cause);
    }

    public InvalidResponseException (String message, Throwable cause) {
        super (message, cause);
    }

    public InvalidResponseException (String message) {
        super (message);
    }
}
