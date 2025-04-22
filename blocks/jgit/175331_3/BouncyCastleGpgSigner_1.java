			CredentialsProvider credentialsProvider
			throws CanceledException
		if (config != null && config.getKeyFormat() != GpgFormat.OPENPGP) {
			throw new UnsupportedSigningFormatException(
					JGitText.get().onlyOpenPgpSupportedForSigning);
		}
