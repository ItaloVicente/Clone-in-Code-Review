
            KeyStore ts = env.sslTruststore();
            if (ts == null) {
                String tsFile = env.sslTruststoreFile();
                if (tsFile != null && !tsFile.isEmpty()) {
                    String tsPassword = env.sslTruststorePassword();
                    char[] tspass = tsPassword == null || tsPassword.isEmpty() ? null : tsPassword.toCharArray();
                    ts = KeyStore.getInstance(KeyStore.getDefaultType());
                    ts.load(new FileInputStream(tsFile), tspass);
                }
            }

            if (ks == null && ts == null) {
                throw new IllegalStateException("Either a KeyStore or a TrustStore " +
                    "need to be provided (or both).");
            } else if (ks == null) {
                ks = ts;
                LOGGER.debug("No KeyStore provided, using provided TrustStore to initialize both factories.");
            } else if (ts == null) {
                ts = ks;
                LOGGER.debug("No TrustStore provided, using provided KeyStore to initialize both factories.");
            }

