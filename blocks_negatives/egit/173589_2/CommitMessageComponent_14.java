	private boolean checkSigningKey(String signingKey,
			@NonNull PersonIdent personIdent) {
		GpgSigner signer = GpgSigner.getDefault();
		if (signer != null) {
			try {
				return signer.canLocateSigningKey(signingKey,
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
				return true;
			}
		}
		return false;
	}

