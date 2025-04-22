
    private static final TrustManager TRUST_MANAGER = new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
            System.err.println(
                "UNKNOWN CLIENT CERTIFICATE: " + chain[0].getSubjectDN());
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
            System.err.println(
                "UNKNOWN SERVER CERTIFICATE: " + chain[0].getSubjectDN());
        }
    };
