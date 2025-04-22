					if (gpgConfig.getKeyFormat() != GpgFormat.OPENPGP) {
						throw new UnsupportedSigningFormatException(
								JGitText.get().onlyOpenPgpSupportedForSigning);
					}
					GpgSigner signer = GpgSigner.getDefault();
					if (!(signer instanceof GpgObjectSigner)) {
						throw new ServiceUnavailableException(
								JGitText.get().signingServiceUnavailable);
