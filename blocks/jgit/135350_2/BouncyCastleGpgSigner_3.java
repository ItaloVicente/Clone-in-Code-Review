		BouncyCastleGpgKeyLocator keyHelper = new BouncyCastleGpgKeyLocator(
				gpgSigningKey

		return keyHelper.findSecretKey();
	}

	@Override
	public void sign(@NonNull CommitBuilder commit
			@Nullable String gpgSigningKey
			CredentialsProvider credentialsProvider) throws CanceledException {
