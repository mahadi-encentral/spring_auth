package com.mamt4real.authentication.ephyto;

import com.mamt4real.authentication.ephyto.exceptions.BillAlreadyConfirmedException;
import com.mamt4real.authentication.ephyto.exceptions.BillNotFoundException;
import com.mamt4real.authentication.ephyto.exceptions.NoPendingBillsException;
import com.mamt4real.authentication.ephyto.exceptions.UnauthorizedException;
import com.mamt4real.authentication.ephyto.responses.BillInfoResponse;
import com.mamt4real.authentication.ephyto.responses.MessageResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IEphyto {

    Optional<BillInfoResponse> getBillInfo(String billNumber) throws BillNotFoundException, UnauthorizedException;
    Optional<BillInfoResponse> getBillInfo(String billNumber, String paymentTransaction) throws BillNotFoundException, UnauthorizedException;

    List<BillInfoResponse> getPendingBills() throws NoPendingBillsException, UnauthorizedException;
    List<BillInfoResponse> getPendingBills(LocalDate dateFrom, LocalDate dateTo) throws NoPendingBillsException, UnauthorizedException;

    List<BillInfoResponse> getCompanyPendingBills(String compRegNumber) throws NoPendingBillsException, UnauthorizedException;
    List<BillInfoResponse> getCompanyPendingBills(String compRegNumber, LocalDate dateFrom, LocalDate dateTo) throws NoPendingBillsException, UnauthorizedException;

    MessageResponse confirmBillPayment(String billNumber) throws BillAlreadyConfirmedException, UnauthorizedException;
    MessageResponse confirmBillPayment(String billNumber, String paymentTransaction) throws BillAlreadyConfirmedException, UnauthorizedException;

}
