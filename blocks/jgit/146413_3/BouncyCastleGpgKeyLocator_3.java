	private BouncyCastleGpgKey loadKeyFromKeybox(Path keybox)
			throws NoOpenPgpKeyException
			NoSuchProviderException
			UnsupportedCredentialItem
		PGPPublicKey publicKey = findPublicKeyInKeyBox(keybox);
		if (publicKey != null) {
			return findSecretKeyForKeyBoxPublicKey(publicKey
		}
		return null;
	}

	private BouncyCastleGpgKey loadKeyFromSecring(Path secring)
			throws IOException
		PGPSecretKey secretKey = findSecretKeyInLegacySecring(signingKey
				secring);

		if (secretKey != null) {
			if (!secretKey.isSigningKey()) {
				throw new PGPException(MessageFormat
						.format(JGitText.get().gpgNotASigningKey
			}
			return new BouncyCastleGpgKey(secretKey
		}
		return null;
	}

