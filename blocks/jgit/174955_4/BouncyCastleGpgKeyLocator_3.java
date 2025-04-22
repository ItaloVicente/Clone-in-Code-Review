		byte[] keyGrip = null;
		try {
			keyGrip = getKeyGrip(publicKey);
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
