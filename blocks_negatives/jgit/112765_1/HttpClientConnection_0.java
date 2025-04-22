		this.hostnameverifier = new X509HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return hostnameverifier.verify(hostname, session);
			}

			@Override
			public void verify(String host, String[] cns, String[] subjectAlts)
					throws SSLException {
			}

			@Override
			public void verify(String host, X509Certificate cert)
					throws SSLException {
			}

			@Override
			public void verify(String host, SSLSocket ssl) throws IOException {
				hostnameverifier.verify(host, ssl.getSession());
			}
		};
