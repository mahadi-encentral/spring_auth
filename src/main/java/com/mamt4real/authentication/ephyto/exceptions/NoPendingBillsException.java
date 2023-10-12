package com.mamt4real.authentication.ephyto.exceptions;

public class NoPendingBillsException extends EphytoCustomException {

    public NoPendingBillsException() {
        super("No pending Bills found", 404);
    }

    public NoPendingBillsException(String comRegNumber) {
        super(String.format("No pending bills found for the company: %s", comRegNumber), 404);

    }
}
