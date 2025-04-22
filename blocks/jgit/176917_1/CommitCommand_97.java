					if (gpgSigner instanceof GpgObjectSigner) {
						((GpgObjectSigner) gpgSigner).signObject(commit
								signingKey
								gpgConfig);
					} else {
						if (gpgConfig.getKeyFormat() != GpgFormat.OPENPGP) {
							throw new UnsupportedSigningFormatException(JGitText
									.get().onlyOpenPgpSupportedForSigning);
						}
						gpgSigner.sign(commit
								credentialsProvider);
					}
