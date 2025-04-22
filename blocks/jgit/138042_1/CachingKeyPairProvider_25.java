		return new CancellingKeyPairIterator(resources);
	}

	@Override
	public Iterable<KeyPair> loadKeys() {
		return this;
