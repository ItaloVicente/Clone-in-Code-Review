package com.couchbase.client.core.env;

public class DefaultCoreProperties {

    public static final boolean SSL_ENABLED = false;
    public static final String SSL_KEYSTORE_FILE = null;
    public static final String SSL_KEYSTORE_PASSWORD = null;
    public static final boolean QUERY_ENABLED = false;
    public static final int QUERY_PORT = 8093;
    public static final boolean BOOTSTRAP_HTTP_ENABLED = true;
    public static final boolean BOOTSTRAP_CARRIER_ENABLED = true;
    public static final int BOOTSTRAP_HTTP_DIRECT_PORT = 8091;
    public static final int BOOTSTRAP_HTTP_SSL_PORT = 18091;
    public static final int BOOTSTRAP_CARRIER_DIRECT_PORT = 11210;
    public static final int BOOTSTRAP_CARRIER_SSL_PORT = 11207;
    public static final int REQUEST_BUFFER_SIZE = 16384;
    public static final int RESPONSE_BUFFER_SIZE = 16384;
    public static final int IO_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    public static final int COMPUTATION_POOL_SIZE =  Runtime.getRuntime().availableProcessors();
    public static final int BINARY_ENDPOINTS = 1;
    public static final int VIEW_ENDPOINTS = 1;
    public static final int QUERY_ENDPOINTS = 1;

}
