					findPublicKeyInKeyBox(USER_KEYBOX_PATH);

			if (publicKey != null) {
				return findSecretKeyForKeyBoxPublicKey(publicKey,
						USER_KEYBOX_PATH);
			}

			throw new PGPException(MessageFormat
					.format(JGitText.get().gpgNoPublicKeyFound, signingKey));
		} else if (exists(USER_PGP_LEGACY_SECRING_FILE)) {
			PGPSecretKey secretKey = findSecretKeyInLegacySecring(signingKey,
					USER_PGP_LEGACY_SECRING_FILE);

			if (secretKey != null) {
				if (!secretKey.isSigningKey()) {
					throw new PGPException(MessageFormat.format(
							JGitText.get().gpgNotASigningKey, signingKey));
