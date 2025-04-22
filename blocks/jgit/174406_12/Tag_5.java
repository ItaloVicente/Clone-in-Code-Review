				if (verify) {
					VerifySignatureCommand verifySig = git.verifySignature()
							.setMode(VerifySignatureCommand.VerifyMode.TAGS)
							.addName(tagName);

					VerificationResult verification = verifySig.call()
							.get(tagName);
					if (verification == null) {
						showUnsigned(git
					} else {
						Throwable error = verification.getException();
						if (error != null) {
							throw die(error.getMessage()
						}
						writeVerification(verifySig.getVerifier().getName()
								(RevTag) verification.getObject()
								verification.getVerification());
					}
				} else if (delete) {
