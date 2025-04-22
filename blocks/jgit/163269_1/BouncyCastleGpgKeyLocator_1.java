			List<Path> allPaths = keyFiles.filter(Files::isRegularFile)
					.collect(Collectors.toCollection(ArrayList::new));
			if (allPaths.isEmpty()) {
				return null;
			}
			PBEProtectionRemoverFactory passphraseProvider = p -> {
				throw new EncryptedPgpKeyException();
			};
			for (int attempts = 0; attempts < 2; attempts++) {
				Iterator<Path> pathIterator = allPaths.iterator();
				while (pathIterator.hasNext()) {
					Path keyFile = pathIterator.next();
					try {
						PGPSecretKey secretKey = attemptParseSecretKey(keyFile
								calculatorProvider
								publicKey);
						pathIterator.remove();
						if (secretKey != null) {
							if (!secretKey.isSigningKey()) {
								throw new PGPException(MessageFormat.format(
										JGitText.get().gpgNotASigningKey
										signingKey));
							}
							return new BouncyCastleGpgKey(secretKey
									userKeyboxPath);
						}
					} catch (EncryptedPgpKeyException e) {
