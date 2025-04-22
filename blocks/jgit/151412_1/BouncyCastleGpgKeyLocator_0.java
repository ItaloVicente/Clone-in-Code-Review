	private PGPPublicKey getPublicKey(KeyBlob blob
			throws IOException {
		return ((PublicKeyRingBlob) blob).getPGPPublicKeyRing()
				.getPublicKey(fingerprint);
	}

