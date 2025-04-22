		if (!hashCodeComputed) {
			hashCode = HASH_INITIAL;
			hashCode = hashCode * HASH_FACTOR + keyStrokes.hashCode();
			hashCodeComputed = true;
		}

		return hashCode;
	}

	public boolean isComplete() {
		return keyStrokes.isEmpty() || ((KeyStroke) keyStrokes.get(keyStrokes.size() - 1)).isComplete();
	}

	public boolean isEmpty() {
		return keyStrokes.isEmpty();
	}

	public boolean startsWith(KeySequence keySequence, boolean equals) {
		if (keySequence == null) {
