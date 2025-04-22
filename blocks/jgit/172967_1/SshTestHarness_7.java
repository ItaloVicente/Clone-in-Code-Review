	private static File createKeyPair(KeyPair newKey
			throws Exception {
		PrivateKey privateKey = newKey.getPrivate();
		String format = privateKey.getFormat();
		if (!"PKCS#8".equalsIgnoreCase(format)) {
			throw new IOException("Cannot write " + privateKey.getAlgorithm()
					+ " key in " + format + " format");
		}
		try (BufferedWriter writer = Files.newBufferedWriter(
				privateKeyFile.toPath()
			writer.write("-----BEGIN PRIVATE KEY-----");
			writer.newLine();
			write(writer
			writer.write("-----END PRIVATE KEY-----");
			writer.newLine();
