		for (KeyStroke keyStroke : potentialKeyStrokes) {
			KeySequence sequenceAfterKeyStroke = KeySequence.getInstance(sequenceBeforeKeyStroke,
					keyStroke);
			if (isPartialMatch(sequenceAfterKeyStroke)) {
				incrementState(sequenceAfterKeyStroke);
				return true;

			} else if (isUniqueMatch(sequenceAfterKeyStroke, createContext)) {
				final ParameterizedCommand cmd = getExecutableMatches(sequenceAfterKeyStroke, context).iterator().next()
						.getParameterizedCommand();
				try {
					return executeCommand(cmd, event) || !sequenceBeforeKeyStroke.isEmpty();
				} catch (final CommandException e) {
