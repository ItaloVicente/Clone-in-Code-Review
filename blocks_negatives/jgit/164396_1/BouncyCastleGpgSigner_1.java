			char[] passphrase = passphrasePrompt.getPassphrase(
					secretKey.getPublicKey().getFingerprint(),
					gpgKey.getOrigin());
			PGPPrivateKey privateKey = secretKey
					.extractPrivateKey(new JcePBESecretKeyDecryptorBuilder()
							.setProvider(BouncyCastleProvider.PROVIDER_NAME)
							.build(passphrase));
