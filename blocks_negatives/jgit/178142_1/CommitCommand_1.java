					if (gpgSigner == null) {
						throw new ServiceUnavailableException(
								JGitText.get().signingServiceUnavailable);
					}
					if (gpgSigner instanceof GpgObjectSigner) {
						((GpgObjectSigner) gpgSigner).signObject(commit,
								signingKey, committer, credentialsProvider,
								gpgConfig);
					} else {
						if (gpgConfig.getKeyFormat() != GpgFormat.OPENPGP) {
							throw new UnsupportedSigningFormatException(JGitText
									.get().onlyOpenPgpSupportedForSigning);
						}
						gpgSigner.sign(commit, signingKey, committer,
								credentialsProvider);
					}
