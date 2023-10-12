package com.mamt4real.authentication.ephyto.exceptions;

public class UnauthorizedException extends  EphytoCustomException{
    public UnauthorizedException() {
        super("No Valid Credentials", 401);
    }
}
