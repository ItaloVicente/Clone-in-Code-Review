	private static KeyManager[] createKeyManagers(final String path
			final char[] password) throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(fis

			KeyManagerFactory keyManagerFactory = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keyManagerFactory.init(keyStore
			return keyManagerFactory.getKeyManagers();
		} catch (KeyStoreException e) {
			throw new IOException(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new IOException(e.getMessage());
		} catch (CertificateException e) {
			throw new IOException(e.getMessage());
		} catch (UnrecoverableKeyException e) {
			throw new IOException(e.getMessage());
		} finally {
			if (fis != null)
				fis.close();
		}
	}

	private static TrustManager[] createTrustManagers(final String path)
			throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			BufferedInputStream bis = new BufferedInputStream(fis);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			Certificate cert = null;
			while (bis.available() > 0)
				cert = cf.generateCertificate(bis);

			KeyStore trustStore = KeyStore.getInstance("JKS");
			trustStore.load(null
			trustStore.setCertificateEntry("trustedCA"

			TrustManagerFactory trustManagerFactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(trustStore);
			return trustManagerFactory.getTrustManagers();
		} catch (CertificateException e) {
			throw new IOException(e.getMessage());
		} catch (KeyStoreException e) {
			throw new IOException(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new IOException(e.getMessage());
		} finally {
			if (fis != null)
				fis.close();
		}
	}

