			PBEProtectionRemoverFactory passphraseProvider = p -> {
				throw new EncryptedPgpKeyException();
			};
			for (int attempts = 0; attempts < 2; attempts++) {
				Iterator<Path> pathIterator = allPaths.iterator();
				while (pathIterator.hasNext()) {
					Path keyFile = pathIterator.next();
					try {
						PGPSecretKey secretKey = attemptParseSecretKey(keyFile,
								calculatorProvider, passphraseProvider,
								publicKey);
						pathIterator.remove();
						if (secretKey != null) {
							if (!secretKey.isSigningKey()) {
								throw new PGPException(MessageFormat.format(
										BCText.get().gpgNotASigningKey,
										signingKey));
							}
							return new BouncyCastleGpgKey(secretKey,
									userKeyboxPath);
						}
					} catch (EncryptedPgpKeyException e) {
					}
				}
				if (attempts > 0 || allPaths.isEmpty()) {
					break;
