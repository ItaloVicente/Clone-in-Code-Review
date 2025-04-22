		List keyStrokes = new ArrayList(keySequence.getKeyStrokes());
		keyStrokes.add(keyStroke);
		return new KeySequence(keyStrokes);
	}

	public static KeySequence getInstance(KeyStroke keyStroke) {
		return new KeySequence(Collections.singletonList(keyStroke));
	}

	public static KeySequence getInstance(KeyStroke[] keyStrokes) {
		return new KeySequence(Arrays.asList(keyStrokes));
	}

	public static KeySequence getInstance(List keyStrokes) {
		return new KeySequence(keyStrokes);
	}
