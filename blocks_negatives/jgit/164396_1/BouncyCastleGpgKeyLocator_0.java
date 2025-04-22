			for (Path keyFile : keyFiles.filter(Files::isRegularFile)
					.collect(Collectors.toList())) {
				PGPSecretKey secretKey = attemptParseSecretKey(keyFile,
						calculatorProvider, passphraseProvider, publicKey);
				if (secretKey != null) {
					if (!secretKey.isSigningKey()) {
						throw new PGPException(MessageFormat.format(
								JGitText.get().gpgNotASigningKey, signingKey));
