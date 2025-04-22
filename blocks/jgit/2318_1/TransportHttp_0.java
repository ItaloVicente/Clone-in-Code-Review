	private void allowSelfSignedCertificate(URLConnection conn)
			throws IOException {
		final TrustManager[] trustAllCerts = new TrustManager[] { new DummyX509TrustManager() };
		try {
			SSLContext ctx = SSLContext.getInstance("SSL");
			ctx.init(null
			final HttpsURLConnection sslConn = (HttpsURLConnection) conn;
			sslConn.setSSLSocketFactory(ctx.getSocketFactory());
		} catch (KeyManagementException e) {
			throw new IOException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new IOException(e);
		}
	}

