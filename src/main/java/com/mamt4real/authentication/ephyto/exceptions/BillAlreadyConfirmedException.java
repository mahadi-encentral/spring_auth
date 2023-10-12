package com.mamt4real.authentication.ephyto.exceptions;

public class BillAlreadyConfirmedException extends  EphytoCustomException{

    public BillAlreadyConfirmedException() {
        super("This bill has already been confirmed", 406);
    }

    public BillAlreadyConfirmedException(String billNumber){
        super(String.format("Bill with bill number: %s has already been confirmed", billNumber), 406);
    }
}
