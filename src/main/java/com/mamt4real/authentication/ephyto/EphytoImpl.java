package com.mamt4real.authentication.ephyto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mamt4real.authentication.ephyto.config.EnvironmentVariables;
import com.mamt4real.authentication.ephyto.exceptions.*;
import com.mamt4real.authentication.ephyto.responses.BillInfoResponse;
import com.mamt4real.authentication.ephyto.responses.MessageResponse;
import org.springframework.stereotype.Service;

import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * @author MAHADI
 * @description The Class that Handles the requests for Ephyto basic endpoints
 * @dateCreated 12/10/2023
 */
@Service
public class EphytoImpl implements IEphyto {

    private final WSClient wsClient;

    private WSRequest getRequest(String url) {
//        Replace with the actual base Url
        return wsClient.url(String.format("https://jo-uat.ephytoexchange.org/gens/api/systems/billing/%s",url))
                .addHeader("secret", EnvironmentVariables.EPHYTO_SECRET)
                .addHeader("client_id", EnvironmentVariables.EPHYTO_CLIENT_ID);
    }

    public EphytoImpl(WSClient wsClient) {
        this.wsClient = wsClient;
    }

    @Override
    public Optional<BillInfoResponse> getBillInfo(String billNumber) throws BillNotFoundException, UnauthorizedException {
        return getBillInfo(billNumber, null);
    }

    @Override
    public Optional<BillInfoResponse> getBillInfo(String billNumber, String paymentTransaction) throws BillNotFoundException, UnauthorizedException {
        String url = String.format("getInfo/%s?%s", billNumber, paymentTransaction == null ? "" : "paymentTransaction=" + paymentTransaction);

        WSResponse response =
                getRequest(url).get().toCompletableFuture().join();

        if (response.getStatus() == 200) {
            return Optional.ofNullable(Json.fromJson(response.asJson(), BillInfoResponse.class));
        } else if (response.getStatus() == 406) {
            throw new BillNotFoundException();
        } else if (response.getStatus() == 401) {
            throw new UnauthorizedException();
        } else {
            throw new EphytoCustomException("Error Retrieving Bill info", response.getStatus());
        }

    }

    @Override
    public List<BillInfoResponse> getPendingBills() throws NoPendingBillsException, UnauthorizedException {
        return getPendingBills(null, null);
    }

    @Override
    public List<BillInfoResponse> getPendingBills(LocalDate dateFrom, LocalDate dateTo) throws NoPendingBillsException, UnauthorizedException {
        String url = "pendingBills";
        return _getPendingBills(url, dateFrom, dateTo);
    }

    @Override
    public List<BillInfoResponse> getCompanyPendingBills(String compRegNumber) throws NoPendingBillsException, UnauthorizedException {
        return getCompanyPendingBills(compRegNumber, null, null);
    }

    @Override
    public List<BillInfoResponse> getCompanyPendingBills(String compRegNumber, LocalDate dateFrom, LocalDate dateTo) throws NoPendingBillsException, UnauthorizedException {
        String url = String.format("pendingCompanyBills/%s", compRegNumber);
        return _getPendingBills(url, dateFrom, dateTo);
    }

    @Override
    public MessageResponse confirmBillPayment(String billNumber) throws BillAlreadyConfirmedException, UnauthorizedException {
        return confirmBillPayment(billNumber, null);
    }

    @Override
    public MessageResponse confirmBillPayment(String billNumber, String paymentTransaction) throws BillAlreadyConfirmedException, UnauthorizedException {
        String url = String.format("confirmPayment/%s?%s", billNumber, paymentTransaction == null ? "" : "paymentTransaction=" + paymentTransaction);
        WSResponse response =
                getRequest(url).get().toCompletableFuture().join();
        if (response.getStatus() == 200) {
            return Json.fromJson(response.asJson(), MessageResponse.class);
        } else if (response.getStatus() == 406) {
            throw new BillAlreadyConfirmedException();
        } else if (response.getStatus() == 401) {
            throw new UnauthorizedException();
        } else {
            throw new EphytoCustomException("Error Confirming Bill", response.getStatus());
        }

    }

    private List<BillInfoResponse> _getPendingBills(String url, LocalDate dateFrom, LocalDate dateTo) throws NoPendingBillsException, UnauthorizedException {
        if (dateFrom != null & dateTo != null) {
            if (dateTo.isBefore(dateFrom)) throw new EphytoCustomException("Invalid date range", 400);
            url = String.format("%s?dateFrom=%s&dateTo=%s",
                    url, dateFrom.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), dateTo.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            );
        }
        WSResponse response =
                getRequest(url).get().toCompletableFuture().join();
        if (response.getStatus() == 200) {
            return Json.mapper().convertValue(response.asJson(), new TypeReference<List<BillInfoResponse>>(){});
        } else if (response.getStatus() == 406) {
            throw new NoPendingBillsException();
        } else if (response.getStatus() == 401) {
            throw new UnauthorizedException();
        } else {
            throw new EphytoCustomException("Error Retrieving Pending Bills", response.getStatus());
        }

    }

}

