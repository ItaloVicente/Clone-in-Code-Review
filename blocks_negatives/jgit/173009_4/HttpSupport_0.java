			throw new IOException(e.getMessage());
		}
	}

	private static class DummyX509TrustManager implements X509TrustManager {
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkClientTrusted(X509Certificate[] certs,
				String authType) {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] certs,
				String authType) {
		}
	}

	private static class DummyHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
