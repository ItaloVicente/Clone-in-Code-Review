	public static void disableSslVerify(HttpConnection conn)
			throws IOException {
		final TrustManager[] trustAllCerts = new TrustManager[] {
				new DummyX509TrustManager() };
		try {
			conn.configure(null
			conn.setHostnameVerifier(new DummyHostnameVerifier());
		} catch (KeyManagementException e) {
			throw new IOException(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new IOException(e.getMessage());
		}
	}

	private static class DummyX509TrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] certs
				String authType) {
		}

		public void checkServerTrusted(X509Certificate[] certs
				String authType) {
		}
	}

	private static class DummyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname
			return true;
		}
	}

