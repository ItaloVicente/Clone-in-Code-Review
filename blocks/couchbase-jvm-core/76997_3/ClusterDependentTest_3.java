        loadProperties();

        DefaultCoreEnvironment.Builder envBuilder = DefaultCoreEnvironment
                .builder();

        if (isMockEnabled()) {
            createMock();
            int httpBootstrapPort = mock.getHttpPort();
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

