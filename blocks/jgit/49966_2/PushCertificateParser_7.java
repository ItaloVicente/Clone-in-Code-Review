	String getAdvertiseNonce() {
		String nonce = sentNonce();
		if (nonce == null) {
			return null;
		}
		return CAPABILITY_PUSH_CERT + '=' + nonce;
