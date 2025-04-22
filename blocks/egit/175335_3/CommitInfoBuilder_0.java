		RevCommit c = commit;
		if (preferences.getBoolean(UIPreferences.HISTORY_VERIFY_SIGNATURES)
				&& c.getRawGpgSignature() != null) {
			GpgSignatureVerifierFactory factory = GpgSignatureVerifierFactory
					.getDefault();
			if (factory != null) {
				GpgSignatureVerifier verifier = factory.getVerifier();
				GpgConfig config = new GpgConfig(db.getConfig());
				try {
					SignatureVerification verification = verifier
							.verifySignature(c, config);
					if (verification != null) {
						String[] text = SignatureUtils
								.toString(verification, committer,
										dateFormatter)
								.split(LF);
						String prefix = verifier.getName();
						for (String line : text) {
							d.append(prefix).append(": ").append(line) //$NON-NLS-1$
									.append(LF);
						}
					}
				} catch (IOException | JGitInternalException e) {
					Activator.logError("Cannot verify signature on commit " //$NON-NLS-1$
							+ commit.name(), e);
				}
			}
		}
