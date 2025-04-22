
	private void showSignature(RevCommit c) throws IOException {
		if (c.getRawGpgSignature() == null) {
			return;
		}
		GpgSignatureVerifierFactory factory = GpgSignatureVerifierFactory
				.getDefault();
		if (factory == null) {
			throw die(CLIText.get().logNoSignatureVerifier
		}
		GpgSignatureVerifier verifier = factory.getVerifier();
		GpgConfig config = new GpgConfig(db.getConfig());
		try {
			SignatureVerification verification = verifier.verifySignature(c
					config);
			if (verification == null) {
				return;
			}
			VerificationUtils.writeVerification(outw
					verifier.getName()
		} finally {
			verifier.clear();
		}
	}
