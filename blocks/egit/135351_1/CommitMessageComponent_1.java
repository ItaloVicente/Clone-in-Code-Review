	private boolean checkSigningKey(String signingKey,
			@NonNull PersonIdent personIdent) {
		boolean signingKeyAvailable;
		GpgSigner signer = GpgSigner.getDefault();
		if (signer != null) {
			try {
				signingKeyAvailable = signer.canLocateSigningKey(signingKey,
						personIdent,
						new CredentialsProvider() {

							@Override
							public boolean supports(CredentialItem... items) {
								return true;
							}

							@Override
							public boolean isInteractive() {
								return false;
							}

							@Override
							public boolean get(URIish uri,
									CredentialItem... items)
									throws UnsupportedCredentialItem {
								return false;
							}
						});
			} catch (CanceledException e) {
				signingKeyAvailable = true;
			}
		} else {
			signingKeyAvailable = false;
		}
		return signingKeyAvailable;
	}

