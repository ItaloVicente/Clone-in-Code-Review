		try {
			for (KeyStroke keyStroke : potentialKeyStrokes) {
				KeySequence sequenceAfterKeyStroke = KeySequence.getInstance(sequenceBeforeKeyStroke,
						keyStroke);
				if (isPartialMatch(sequenceAfterKeyStroke)) {
					incrementState(sequenceAfterKeyStroke);
