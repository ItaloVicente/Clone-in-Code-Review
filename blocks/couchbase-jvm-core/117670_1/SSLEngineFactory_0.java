
            if (env.sslHostnameVerificationEnabled()) {
                SSLParameters sslParameters = engine.getSSLParameters();
                sslParameters.setEndpointIdentificationAlgorithm("HTTPS");
                engine.setSSLParameters(sslParameters);
            }
