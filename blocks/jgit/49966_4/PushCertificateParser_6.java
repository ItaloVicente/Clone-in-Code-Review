		String nonce = sentNonce();
		if (nonce == null) {
			return null;
		}
		return CAPABILITY_PUSH_CERT + '=' + nonce;
	}

	private String sentNonce() {
		if (sentNonce == null && nonceGenerator != null) {
			sentNonce = nonceGenerator.createNonce(db
					TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
		}
		return sentNonce;
