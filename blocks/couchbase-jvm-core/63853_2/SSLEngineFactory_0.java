
            KeyStore ks = env.sslKeystore();
            if (ks == null) {
                ks = KeyStore.getInstance(KeyStore.getDefaultType());
                String ksFile = env.sslKeystoreFile();
                if (ksFile == null || ksFile.isEmpty()) {
                    throw new IllegalArgumentException("Path to Keystore File must not be null or empty.");
                }
                ks.load(new FileInputStream(ksFile), password);
