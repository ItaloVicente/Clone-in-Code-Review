		if ("https".equals(u.getProtocol())) {
			CertPassword certPasswordItem = new CertPassword(http.sslKey);
			CredentialsProvider credentialsProvider = getCredentialsProvider();
			if (credentialsProvider != null)
				credentialsProvider.get(new URIish(u)

			KeyManager[] keyManagers = null;
			if (http.sslKey != null)
				keyManagers = createKeyManagers(http.sslKey
						certPasswordItem.getValue());

			TrustManager[] trustManagers = null;
			if (http.sslCAInfo != null)
				trustManagers = createTrustManagers(http.sslCAInfo);

			configureSsl(conn
