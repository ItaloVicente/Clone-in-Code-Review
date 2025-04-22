	private BouncyCastleGpgKey loadKeyFromKeybox(Path keybox)
			throws NoOpenPgpKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, IOException, CanceledException,
			UnsupportedCredentialItem, PGPException, URISyntaxException {
		PGPPublicKey publicKey = findPublicKeyInKeyBox(keybox);
		if (publicKey != null) {
			return findSecretKeyForKeyBoxPublicKey(publicKey, keybox);
