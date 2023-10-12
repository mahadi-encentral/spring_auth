package com.mamt4real.authentication.ephyto.config;

import java.util.Optional;

/**
 * Defines Environment Variables
 */
public class EnvironmentVariables {

    public static final String EPHYTO_SECRET = Optional.ofNullable(System.getenv("EPHYTO_SECRET")).orElse("default_ephyto_secret");
    public static final String EPHYTO_CLIENT_ID = Optional.ofNullable(System.getenv("EPHYTO_CLIENT_ID")).orElse("default_ephyto_client_id");

}