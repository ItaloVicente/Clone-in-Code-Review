		KeySequence castedObject = (KeySequence) object;
		int compareTo = Util.compare(keyStrokes, castedObject.keyStrokes);
		return compareTo;
	}

	public boolean endsWith(KeySequence keySequence, boolean equals) {
		if (keySequence == null) {
