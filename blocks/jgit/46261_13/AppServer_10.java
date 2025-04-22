	private SslContextFactory createTestSslContextFactory(String hostName) {
		SslContextFactory factory = new SslContextFactory(true);

		String dName = "CN=

		try {
			File tmpDir = Files.createTempDirectory("jks").toFile();
			tmpDir.deleteOnExit();
			makePrivate(tmpDir);
			File keyStore = new File(tmpDir
			Runtime.getRuntime().exec(
					new String[] {
							"keytool"
							"-keystore"
							"-storepass"
							"-alias"
							"-genkeypair"
							"-keyalg"
							"-keypass"
							"-dname"
							"-validity"
					}).waitFor();
			keyStore.deleteOnExit();
			makePrivate(keyStore);
			filesToDelete.add(keyStore);
			filesToDelete.add(tmpDir);
			factory.setKeyStorePath(keyStore.getAbsolutePath());
			factory.setKeyStorePassword(keyPassword);
			factory.setKeyManagerPassword(keyPassword);
			factory.setTrustStorePath(keyStore.getAbsolutePath());
			factory.setTrustStorePassword(keyPassword);
		} catch (InterruptedException | IOException e) {
			throw new RuntimeException("Cannot create ssl key/certificate"
		}
		return factory;
	}

	private void makePrivate(File file) {
		file.setReadable(false);
		file.setWritable(false);
		file.setExecutable(false);
		file.setReadable(true
		file.setWritable(true
		if (file.isDirectory()) {
			file.setExecutable(true
		}
	}

