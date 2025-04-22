
	@Override
	public boolean canLocateSigningKey(String gpgSigningKey
			PersonIdent committer
			throws CanceledException {
		if (gpgSigningKey == null || gpgSigningKey.isEmpty()) {
			gpgSigningKey = committer.getEmailAddress();
		}

		try (BouncyCastleGpgKeyPassphrasePrompt passphrasePrompt = new BouncyCastleGpgKeyPassphrasePrompt(
				credentialsProvider)) {
			BouncyCastleGpgKeyLocator keyHelper = new BouncyCastleGpgKeyLocator(
					gpgSigningKey

			BouncyCastleGpgKey gpgKey = keyHelper.findSecretKey();
			PGPSecretKey secretKey = gpgKey.getSecretKey();
			return secretKey != null;
		} catch (PGPException | IOException | URISyntaxException e) {
			return false;
		}
	}
