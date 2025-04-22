
	private static class DummyX509TrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] certs
		}

		public void checkServerTrusted(X509Certificate[] certs
		}
	}
