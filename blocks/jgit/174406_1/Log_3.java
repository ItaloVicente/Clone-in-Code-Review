	private void showSignature(RevCommit c) throws IOException {
		if (c.getRawGpgSignature() == null) {
			return;
		}
		if (verifier == null) {
			GpgSignatureVerifierFactory factory = GpgSignatureVerifierFactory
					.getDefault();
			if (factory == null) {
				throw die(CLIText.get().logNoSignatureVerifier
			}
			verifier = factory.getVerifier();
		}
		SignatureVerification verification = verifier.verifySignature(c);
		if (verification == null) {
			return;
		}
		VerificationUtils.writeVerification(outw
				verifier.getName()
	}

