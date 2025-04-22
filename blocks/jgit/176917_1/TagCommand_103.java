		}
		if (!isAnnotated()) {
			if ((message != null && !message.isEmpty()) || tagger != null) {
				throw new JGitInternalException(JGitText
						.get().messageAndTaggerNotAllowedInUnannotatedTags);
			}
		} else {
			if (tagger == null) {
				tagger = new PersonIdent(repo);
			}
			if (!(Boolean.FALSE.equals(signed) && signingKey == null)) {
				if (gpgConfig == null) {
					gpgConfig = new GpgConfig(repo.getConfig());
				}
				boolean doSign = isSigned() || gpgConfig.isSignAllTags();
				if (!Boolean.TRUE.equals(annotated) && !doSign) {
					doSign = gpgConfig.isSignAnnotated();
				}
				if (doSign) {
					if (signingKey == null) {
						signingKey = gpgConfig.getSigningKey();
					}
					if (gpgSigner == null) {
						GpgSigner signer = GpgSigner.getDefault();
						if (!(signer instanceof GpgObjectSigner)) {
							throw new ServiceUnavailableException(
									JGitText.get().signingServiceUnavailable);
						}
						gpgSigner = (GpgObjectSigner) signer;
					}
					if (message != null && !message.isEmpty()
						message += '\n';
					}
				}
			}
		}
