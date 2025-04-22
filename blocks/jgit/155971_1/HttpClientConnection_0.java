	private SSLConnectionSocketFactory getSSLSocketFactory() {
		HostnameVerifier verifier = hostnameverifier;
		SSLContext context;
		if (verifier == null) {
			context = SSLContexts.createDefault();
			verifier = new DefaultHostnameVerifier(
					PublicSuffixMatcherLoader.getDefault());
		} else {
			context = getSSLContext();
		}
		return new SSLConnectionSocketFactory(context

			@Override
			protected void prepareSocket(SSLSocket socket) throws IOException {
				super.prepareSocket(socket);
				HttpSupport.configureTLS(socket);
			}
		};
	}

