
	private static class DummyX509TrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] certs, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] certs, String authType) {
		}
	}

	private static class DummyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
