
            KeyStore ts = env.sslTruststore();
            if (ts == null) {
                String tsFile = env.sslTruststoreFile();
                if (tsFile == null || tsFile.isEmpty()) {
                    ts = ks;
                } else {
                    String tsPassword = env.sslTruststorePassword();
                    char[] tspass = tsPassword == null || tsPassword.isEmpty() ? null : tsPassword.toCharArray();
                    ts = KeyStore.getInstance(KeyStore.getDefaultType());
                    ts.load(new FileInputStream(tsFile), tspass);
                }
            }

