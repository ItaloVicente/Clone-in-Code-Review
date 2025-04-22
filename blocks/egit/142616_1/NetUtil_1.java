	private static final TrustManager[] TRUST_ALL_CERTS = new TrustManager[] {
			new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
