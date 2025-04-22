            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            char[] password = env.sslKeystorePassword().isEmpty() ? null : env.sslKeystorePassword().toCharArray();
            ks.load(new FileInputStream(env.sslKeystoreFile()), password);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(ALGORITHM);
            kmf.init(ks, password);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(ALGORITHM);
            tmf.init(ks);

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            SSLEngine engine = ctx.createSSLEngine();
