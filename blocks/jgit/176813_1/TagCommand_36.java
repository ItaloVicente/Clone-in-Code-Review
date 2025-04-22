					if (gpgSigner == null) {
						GpgSigner signer = GpgSigner.getDefault();
						if (!(signer instanceof GpgObjectSigner)) {
							throw new ServiceUnavailableException(
									JGitText.get().signingServiceUnavailable);
						}
						gpgSigner = (GpgObjectSigner) signer;
