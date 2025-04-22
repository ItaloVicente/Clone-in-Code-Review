		try {
			return canLocateSigningKey(gpgSigningKey
					credentialsProvider
		} catch (UnsupportedSigningFormatException e) {
			return false;
		}
	}

	@Override
	public boolean canLocateSigningKey(@Nullable String gpgSigningKey
			PersonIdent committer
			GpgConfig config)
			throws CanceledException
		if (config != null && config.getKeyFormat() != GpgFormat.OPENPGP) {
			throw new UnsupportedSigningFormatException(
					JGitText.get().onlyOpenPgpSupportedForSigning);
		}
