		byte[] keyGrip = null;
		try {
			keyGrip = KeyGrip.getKeyGrip(publicKey);
		} catch (PGPException e) {
			throw new PGPException(
					MessageFormat.format(BCText.get().gpgNoKeygrip
							Hex.toHexString(publicKey.getFingerprint()))
					e);
		}
		String filename = Hex.toHexString(keyGrip).toUpperCase(Locale.ROOT)
		Path keyFile = USER_SECRET_KEY_DIR.resolve(filename);
		if (!Files.exists(keyFile)) {
			return null;
		}
		boolean clearPrompt = false;
		try {
			PGPDigestCalculatorProvider calculatorProvider = new JcaPGPDigestCalculatorProviderBuilder()
					.build();
			clearPrompt = true;
			PGPSecretKey secretKey = null;
			try {
				secretKey = attemptParseSecretKey(keyFile
						() -> passphrasePrompt.getPassphrase(
								publicKey.getFingerprint()
						publicKey);
			} catch (PGPException e) {
				throw new PGPException(MessageFormat.format(
						BCText.get().gpgFailedToParseSecretKey
						keyFile.toAbsolutePath())
