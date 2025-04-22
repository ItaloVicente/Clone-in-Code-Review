						passphraseProvider, publicKey);
			} catch (EncryptedPgpKeyException e) {
				passphraseProvider = new JcePBEProtectionRemoverFactory(
						passphrasePrompt.getPassphrase(
								publicKey.getFingerprint(), userKeyboxPath));
				clearPrompt = true;
				try {
					secretKey = attemptParseSecretKey(keyFile, calculatorProvider,
							passphraseProvider, publicKey);
				} catch (PGPException e1) {
					throw new PGPException(MessageFormat.format(
							BCText.get().gpgFailedToParseSecretKey,
							keyFile.toAbsolutePath()), e);

				}
