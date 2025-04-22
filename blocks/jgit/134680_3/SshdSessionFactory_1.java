	private KeyPairProvider toKeyPairProvider(Iterable<KeyPair> keys) {
		if (keys instanceof KeyPairProvider) {
			return (KeyPairProvider) keys;
		}
		return () -> keys;
	}

