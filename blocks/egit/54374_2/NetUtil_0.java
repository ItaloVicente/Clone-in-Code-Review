
	public static CloseableHttpClient createSSLConfiguredHttpClient(
			Repository repo)
			throws IOException {
		CloseableHttpClient httpclient = null;
		X509HostnameVerifier verifier = null;
		try {
			SSLContext sslcontext = null;
			if (!repo.getConfig().getBoolean("http", "sslVerify", true)) { //$NON-NLS-1$ //$NON-NLS-2$
				sslcontext = SSLContext
						.getInstance(SSLConnectionSocketFactory.TLS);
				sslcontext.init(null, trustAllCerts, null);
				verifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			} else {
				sslcontext = SSLContext.getDefault();
				verifier = SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
			}
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslcontext, new String[] { "TLSv1", "TLSv1.1", "TLSv1.2" }, //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
					null, verifier);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf)
					.build();
		} catch (KeyManagementException e) {
			throw new IOException(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new IOException(e.getMessage());
		}
		return httpclient;
	}
