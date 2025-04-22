
		GpgConfig gpgConfig = new GpgConfig(repo.getConfig());
		if (signCommit == null) {
			signCommit = gpgConfig.isSignCommits() ? Boolean.TRUE
					: Boolean.FALSE;
		}
		if (signingKey == null) {
			signingKey = gpgConfig.getSigningKey();
		}
		if (gpgSigner == null) {
			if (gpgConfig.getKeyFormat() != GpgFormat.OPENPGP) {
				throw new UnsupportedSigningFormatException(
						JGitText.get().onlyOpenPgpSupportedForSigning);
			}
			gpgSigner = GpgSigner.getDefault();
			if (gpgSigner == null) {
				gpgSigner = new BouncyCastleGpgSigner();
			}
		}
