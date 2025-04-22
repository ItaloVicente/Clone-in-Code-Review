
    boolean sslEnabled();

    String sslKeystoreFile();
    String sslKeystorePassword();

    boolean queryEnabled();
    int queryPort();

    boolean bootstrapHttpEnabled();
    boolean bootstrapCarrierEnabled();

    int bootstrapHttpDirectPort();

    int bootstrapHttpSslPort();

    int bootstrapCarrierDirectPort();

    int bootstrapCarrierSslPort();

    int ioPoolSize();

    int computationPoolSize();


    int requestBufferSize();

    int responseBufferSize();

    int binaryServiceEndpoints();

    int viewServiceEndpoints();


    int queryServiceEndpoints();
