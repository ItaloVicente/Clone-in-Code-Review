
	private class DummyX509TrustManager implements X509TrustManager {

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] certs
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] certs
		}
	}
