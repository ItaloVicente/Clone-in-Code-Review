			JcePBESecretKeyDecryptorBuilder decryptorBuilder = new JcePBESecretKeyDecryptorBuilder()
					.setProvider(BouncyCastleProvider.PROVIDER_NAME);
			PGPPrivateKey privateKey = null;
			if (!passphrasePrompt.hasPassphrase()) {
				try {
					privateKey = secretKey.extractPrivateKey(
							decryptorBuilder.build(new char[0]));
				} catch (PGPException e) {
				}
			}
			if (privateKey == null) {
				char[] passphrase = passphrasePrompt.getPassphrase(
						secretKey.getPublicKey().getFingerprint()
						gpgKey.getOrigin());
				privateKey = secretKey
						.extractPrivateKey(decryptorBuilder.build(passphrase));
			}
