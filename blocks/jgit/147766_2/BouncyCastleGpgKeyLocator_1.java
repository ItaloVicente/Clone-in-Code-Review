		}
		if (publicKey != null) {
			throw new PGPException(MessageFormat.format(
					JGitText.get().gpgNoSecretKeyForPublicKey
					Long.toHexString(publicKey.getKeyID())));
		} else if (hasSecring) {
