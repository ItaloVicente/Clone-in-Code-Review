				if (attempts > 0 || allPaths.isEmpty()) {
					break;
				}
				passphraseProvider = new JcePBEProtectionRemoverFactory(
						passphrasePrompt.getPassphrase(
								publicKey.getFingerprint()
