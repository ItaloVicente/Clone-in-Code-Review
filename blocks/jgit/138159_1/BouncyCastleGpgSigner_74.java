	public boolean canLocateSigningKey(@Nullable String gpgSigningKey
			PersonIdent committer
			throws CanceledException {
		try (BouncyCastleGpgKeyPassphrasePrompt passphrasePrompt = new BouncyCastleGpgKeyPassphrasePrompt(
				credentialsProvider)) {
			BouncyCastleGpgKey gpgKey = locateSigningKey(gpgSigningKey
					committer
			return gpgKey != null;
		} catch (PGPException | IOException | URISyntaxException e) {
			return false;
		}
	}

	private BouncyCastleGpgKey locateSigningKey(@Nullable String gpgSigningKey
			PersonIdent committer
			BouncyCastleGpgKeyPassphrasePrompt passphrasePrompt)
			throws CanceledException
			PGPException
