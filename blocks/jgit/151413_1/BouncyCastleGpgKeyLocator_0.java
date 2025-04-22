	private PGPPublicKey getSigningPublicKey(KeyBlob blob) throws IOException {
		PGPPublicKey masterKey = null;
		Iterator<PGPPublicKey> keys = ((PublicKeyRingBlob) blob)
				.getPGPPublicKeyRing().getPublicKeys();
		while (keys.hasNext()) {
			PGPPublicKey key = keys.next();
			if (isSigningKey(key)) {
				if (key.isMasterKey()) {
					masterKey = key;
				} else {
					return key;
				}
			}
		}
		return masterKey;
	}

	private boolean isSigningKey(PGPPublicKey key) {
		Iterator signatures = key.getSignatures();
		while (signatures.hasNext()) {
			PGPSignature sig = (PGPSignature) signatures.next();
			if ((sig.getHashedSubPackets().getKeyFlags()
					& PGPKeyFlags.CAN_SIGN) > 0) {
				return true;
			}
		}
		return false;
	}

