						() -> passphrasePrompt.getPassphrase(
								publicKey.getFingerprint()
						publicKey);
			} catch (PGPException e) {
				throw new PGPException(MessageFormat.format(
						BCText.get().gpgFailedToParseSecretKey
						keyFile.toAbsolutePath())
