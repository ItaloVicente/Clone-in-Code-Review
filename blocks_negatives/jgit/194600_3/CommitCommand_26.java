		if (signingKey == null) {
			signingKey = gpgConfig.getSigningKey();
		}
		if (gpgSigner == null) {
			gpgSigner = GpgSigner.getDefault();
		}
