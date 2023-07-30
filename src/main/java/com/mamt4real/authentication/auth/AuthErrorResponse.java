package com.mamt4real.authentication.auth;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AuthErrorResponse {
    private String status;
    private String message;
    private String errorMessage;
}
