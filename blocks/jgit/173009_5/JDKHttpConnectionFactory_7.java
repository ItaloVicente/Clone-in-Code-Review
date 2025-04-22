
	@Override
	public GitSession newSession() {
		return new JdkConnectionSession();
	}

	private static class JdkConnectionSession implements GitSession {

		private SSLContext securityContext;

		private SSLSocketFactory socketFactory;

		@Override
		public JDKHttpConnection configure(HttpConnection connection
				boolean sslVerify) throws GeneralSecurityException {
			if (!(connection instanceof JDKHttpConnection)) {
				throw new IllegalArgumentException(MessageFormat.format(
						JGitText.get().httpWrongConnectionType
						JDKHttpConnection.class.getName()
						connection.getClass().getName()));
			}
			JDKHttpConnection conn = (JDKHttpConnection) connection;
			String scheme = conn.getURL().getProtocol();
				return conn;
			}
			if (securityContext == null) {
				TrustManager[] trustAllCerts = {
						new NoCheckX509TrustManager() };
				securityContext.init(null
				socketFactory = new DelegatingSSLSocketFactory(
						securityContext.getSocketFactory()) {

					@Override
					protected void configure(SSLSocket socket) {
						HttpSupport.configureTLS(socket);
					}
				};
			}
			conn.setHostnameVerifier((name
			((HttpsURLConnection) conn.wrappedUrlConnection)
					.setSSLSocketFactory(socketFactory);
			return conn;
		}

		@Override
		public void close() {
			securityContext = null;
			socketFactory = null;
		}
	}

