		try {
			return new KeySequence(keyStrokes);
		} catch (Throwable t) {
			throw new ParseException("Could not construct key sequence with these key strokes: " //$NON-NLS-1$
					+ keyStrokes);
		}
	}

	private transient int hashCode;

	private transient boolean hashCodeComputed;

	private List keyStrokes;

	private KeySequence(List keyStrokes) {
		this.keyStrokes = Util.safeCopy(keyStrokes, KeyStroke.class);

		for (int i = 0; i < this.keyStrokes.size() - 1; i++) {
			KeyStroke keyStroke = (KeyStroke) this.keyStrokes.get(i);

			if (!keyStroke.isComplete()) {
