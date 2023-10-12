package com.mamt4real.authentication.ephyto.exceptions;

public class EphytoCustomException extends RuntimeException{
    private int statusCode;

    public EphytoCustomException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
