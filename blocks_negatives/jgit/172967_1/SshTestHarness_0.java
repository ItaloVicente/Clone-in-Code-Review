	private static byte[] createHostKey(OutputStream publicKey)
			throws Exception {
		JSch jsch = new JSch();
		KeyPair pair = KeyPair.genKeyPair(jsch, KeyPair.RSA, 2048);
		pair.writePublicKey(publicKey, "");
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			pair.writePrivateKey(out);
			out.flush();
			return out.toByteArray();
