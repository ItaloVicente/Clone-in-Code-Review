			gpgSigner = GpgSigner.getDefault();
			if (gpgSigner == null) {
				throw new ServiceUnavailableException(
						JGitText.get().signingServiceUnavailable);
			}
		}
		if (signingKey == null) {
			signingKey = gpgConfig.getSigningKey();
