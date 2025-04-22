        if (auth instanceof CertAuthenticator) {
            if (!environment.certAuthEnabled()) {
                throw new AuthenticationException("CertAuthenticator used, but certAuthEnabled not enabled on " +
                    "the Environment");
            }

            if (environment.sslKeystore() == null &&
                environment.sslTruststore() == null &&
                (environment.sslKeystoreFile() == null || environment.sslKeystoreFile().isEmpty()) &&
                (environment.sslTruststoreFile() == null || environment.sslTruststoreFile().isEmpty())) {
                throw new AuthenticationException("CertAuthenticator used, but neither keystore nor " +
                    "truststore configured");
            }
        } else if (environment.certAuthEnabled()) {
            throw new AuthenticationException("Only CertAuthenticator can be used when certAuthEnabled on the " +
                "Environment");
        }

