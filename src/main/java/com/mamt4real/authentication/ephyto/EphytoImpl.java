package com.mamt4real.authentication.ephyto;

import com.mamt4real.authentication.ephyto.exceptions.*;
import com.mamt4real.authentication.ephyto.responses.BillInfoResponse;
import com.mamt4real.authentication.ephyto.responses.MessageResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    private final WebClient ephytoWebClient;

    public EphytoImpl(@Qualifier("ephytoWebClient") WebClient ephytoWebClient) {
        this.ephytoWebClient = ephytoWebClient;
    }

    @Override
    public Optional<BillInfoResponse> getBillInfo(String billNumber) throws BillNotFoundException, UnauthorizedException {
        return getBillInfo(billNumber, null);
    }

    @Override
    public Optional<BillInfoResponse> getBillInfo(String billNumber, String paymentTransaction) throws BillNotFoundException, UnauthorizedException {
        String url = String.format("getInfo/%s?%s", billNumber, paymentTransaction == null ? "" : "paymentTransaction=" + paymentTransaction);
        return ephytoWebClient.get()
                .uri(url)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> {
                            if (clientResponse.statusCode().is4xxClientError()) {
                                if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                                    return Mono.error(new BillNotFoundException());
                                } else if (clientResponse.statusCode().equals(HttpStatus.UNAUTHORIZED)) {
                                    return Mono.error(new UnauthorizedException());
                                }
                            }
                            return Mono.error(new EphytoCustomException("Error Retrieving Bill info", clientResponse.statusCode().value()));
                        }
                )
                .bodyToMono(BillInfoResponse.class).blockOptional();
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
        return ephytoWebClient.get()
                .uri(url)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> {
                            if (clientResponse.statusCode().is4xxClientError()) {
                                if (clientResponse.statusCode().equals(HttpStatus.NOT_ACCEPTABLE)) {
                                    return Mono.error(new BillAlreadyConfirmedException());
                                } else if (clientResponse.statusCode().equals(HttpStatus.UNAUTHORIZED)) {
                                    return Mono.error(new UnauthorizedException());
                                }
                            }
                            return Mono.error(new EphytoCustomException("Error Confirming Bill", clientResponse.statusCode().value()));
                        }
                )
                .bodyToMono(MessageResponse.class).block();
    }

    private List<BillInfoResponse> _getPendingBills(String url, LocalDate dateFrom, LocalDate dateTo) throws NoPendingBillsException, UnauthorizedException {
        if (dateFrom != null & dateTo != null) {
            if (dateTo.isBefore(dateFrom)) throw new EphytoCustomException("Invalid date range", 400);
            url = String.format("%s?dateFrom=%s&dateTo=%s",
                    url, dateFrom.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), dateTo.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            );
        }
        return ephytoWebClient.get()
                .uri(url)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> {
                            if (clientResponse.statusCode().is4xxClientError()) {
                                if (clientResponse.statusCode().equals(HttpStatus.NOT_ACCEPTABLE)) {
                                    return Mono.error(new NoPendingBillsException());
                                } else if (clientResponse.statusCode().equals(HttpStatus.UNAUTHORIZED)) {
                                    return Mono.error(new UnauthorizedException());
                                }
                            }
                            return Mono.error(new EphytoCustomException("Error Retrieving Pending Bills", clientResponse.statusCode().value()));
                        }
                )
                .bodyToFlux(BillInfoResponse.class).collectList().block();
    }

}
