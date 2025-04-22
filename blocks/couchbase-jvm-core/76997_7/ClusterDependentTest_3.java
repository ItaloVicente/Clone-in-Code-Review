    private static int getCarrierPortInfo(int httpPort) throws Exception {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost("localhost").setPort(httpPort).setPath("mock/get_mcports")
                .setParameter("bucket", bucket);
        HttpGet request = new HttpGet(builder.build());
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);
        int status = response.getStatusLine().getStatusCode();
        if (status < 200 || status > 300) {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
        String rawBody = EntityUtils.toString(response.getEntity());
        com.google.gson.JsonObject respObject = JsonUtils.GSON.fromJson(rawBody, com.google.gson.JsonObject.class);
        com.google.gson.JsonArray portsArray = respObject.getAsJsonArray("payload");
        return portsArray.get(0).getAsInt();
    }


    public static void connect(boolean useMock) {
        DefaultCoreEnvironment.Builder envBuilder = DefaultCoreEnvironment
                .builder();

        if (useMock) {
            int httpBootstrapPort = couchbaseMock.getHttpPort();
            try {
                int carrierBootstrapPort = getCarrierPortInfo(httpBootstrapPort);
                envBuilder
                        .bootstrapHttpDirectPort(httpBootstrapPort)
                        .bootstrapCarrierDirectPort(carrierBootstrapPort)
                        .socketConnectTimeout(30000);
            } catch (Exception ex) {
                throw new RuntimeException("Unable to get port info" + ex.getMessage(), ex);
            }

        }
        env = envBuilder.dcpEnabled(true)
                .dcpConnectionBufferSize(1024)          // 1 kilobyte
                .dcpConnectionBufferAckThreshold(0.5)   // should trigger BUFFER_ACK after 512 bytes
                .mutationTokensEnabled(true)
                .keepAliveInterval(KEEPALIVE_INTERVAL)
                .build();

