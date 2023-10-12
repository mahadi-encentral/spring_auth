package com.mamt4real.authentication.ephyto.exceptions;

public class BillNotFoundException extends EphytoCustomException{

    public BillNotFoundException() {
        super("Bill not found", 404);
    }

    public BillNotFoundException(String billNumber){
        super(String.format("Bill with bill number: %s does not exist", billNumber), 404);
    }
}
