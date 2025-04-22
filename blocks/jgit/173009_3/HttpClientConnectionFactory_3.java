
	@Override
	public GitSession newSession() {
		return new HttpClientSession();
	}

	private static class HttpClientSession implements GitSession {

		private SSLContext securityContext;

		private SSLConnectionSocketFactory socketFactory;

		private boolean isDefault;

		@Override
		public HttpClientConnection configure(HttpConnection connection
				boolean sslVerify)
				throws IOException
			if (!(connection instanceof HttpClientConnection)) {
				throw new IllegalArgumentException(MessageFormat.format(
						HttpApacheText.get().httpWrongConnectionType
						HttpClientConnection.class.getName()
						connection.getClass().getName()));
			}
			HttpClientConnection conn = (HttpClientConnection) connection;
			String scheme = conn.getURL().getProtocol();
				return conn;
			}
			if (securityContext == null || isDefault != sslVerify) {
				isDefault = sslVerify;
				HostnameVerifier verifier;
				if (sslVerify) {
					securityContext = SSLContext.getDefault();
					verifier = SSLConnectionSocketFactory
							.getDefaultHostnameVerifier();
				} else {
					securityContext = SSLContext.getInstance("TLS");
					TrustManager[] trustAllCerts = {
							new NoCheckX509TrustManager() };
					securityContext.init(null
					verifier = (name
				}
				socketFactory = new SSLConnectionSocketFactory(securityContext
						verifier) {

					@Override
					protected void prepareSocket(SSLSocket socket)
							throws IOException {
						super.prepareSocket(socket);
						HttpSupport.configureTLS(socket);
					}
				};
			}
			conn.setSSLSocketFactory(socketFactory
			return conn;
		}

		@Override
		public void close() {
			securityContext = null;
			socketFactory = null;
		}

	}
