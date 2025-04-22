	private void disableSslVerify(HttpConnection conn)
			throws IOException {
		final TrustManager[] trustAllCerts = new TrustManager[] { new DummyX509TrustManager() };
		try {
			conn.configure(null, trustAllCerts, null);
			conn.setHostnameVerifier(new DummyHostnameVerifier());
		} catch (KeyManagementException e) {
			throw new IOException(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new IOException(e.getMessage());
		}
	}

