            if (createAdhocBucket) {
                this.bucketName = AD_HOC + this.bucketName + System.nanoTime();
            }

            loadProperties();

            if (isMockEnabled()) {
                createMock();
                int httpBootstrapPort = this.couchbaseMock.getHttpPort();
                try {
                    int carrierBootstrapPort = getCarrierPortInfo(httpBootstrapPort);
                    envBuilder
                            .bootstrapHttpDirectPort(httpBootstrapPort)
                            .bootstrapCarrierDirectPort(carrierBootstrapPort)
                            .connectTimeout(30000);
                } catch (Exception ex) {
                    throw new RuntimeException("Unable to get port info" + ex.getMessage(), ex);
                }
            }
