		((HttpsURLConnection) wrappedUrlConnection).setSSLSocketFactory(
				new DelegatingSSLSocketFactory(ctx.getSocketFactory()) {

					@Override
					protected void configure(SSLSocket socket)
							throws IOException {
						HttpSupport.configureTLS(socket);
					}
				});
