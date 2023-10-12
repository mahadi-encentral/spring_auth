//package com.mamt4real.authentication.ephyto.config;
//
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.client.WebClient;
//
///**
// * @author MAHADI
// * @description The Default client for making http request for ephyto endpoints
// * @dateCreated 12/10/2023
// *
// */
//@Configuration
//public class WebClientConfig {
//    private final EphytoProperties ephytoProperties;
//
//    @Autowired
//    public WebClientConfig(EphytoProperties ephytoProperties) {
//        this.ephytoProperties = ephytoProperties;
//    }
//    @Bean
//    public WebClient ephytoWebClient() {
//        return WebClient.builder()
//                .defaultHeader("secret", ephytoProperties.getSecret())
//                .defaultHeader("client_id", ephytoProperties.getClientId())
//                .baseUrl("https://jo-uat.ephytoexchange.org/gens/api/systems/billing/")
//                .build();
//    }
//}

//        return ephytoWebClient.get()
//                .uri(url)
//                .retrieve()
//                .onStatus(
//                        HttpStatusCode::is4xxClientError,
//                        clientResponse -> {
//                            if (clientResponse.statusCode().is4xxClientError()) {
//                                if (clientResponse.statusCode().equals(HttpStatus.NOT_ACCEPTABLE)) {
//                                    return Mono.error(new NoPendingBillsException());
//                                } else if (clientResponse.statusCode().equals(HttpStatus.UNAUTHORIZED)) {
//                                    return Mono.error(new UnauthorizedException());
//                                }
//                            }
//                            return Mono.error(new EphytoCustomException("Error Retrieving Pending Bills", clientResponse.statusCode().value()));
//                        }
//                )
//                .bodyToFlux(BillInfoResponse.class).collectList().block();
