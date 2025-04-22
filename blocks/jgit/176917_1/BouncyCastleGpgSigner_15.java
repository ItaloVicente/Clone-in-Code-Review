		try {
			signObject(commit
					null);
		} catch (UnsupportedSigningFormatException e) {
		}
	}

	@Override
	public void signObject(@NonNull ObjectBuilder object
			@Nullable String gpgSigningKey
			CredentialsProvider credentialsProvider
			throws CanceledException
		if (config != null && config.getKeyFormat() != GpgFormat.OPENPGP) {
			throw new UnsupportedSigningFormatException(
					JGitText.get().onlyOpenPgpSupportedForSigning);
		}
