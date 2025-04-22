	/**
	 * Determines whether the key sequence is a perfect match for any command. If there is a match,
	 * then the corresponding command identifier is returned.
	 *
	 * @param keySequence
	 *            The key sequence to check for a match; must never be <code>null</code>.
	 * @return The perfectly matching command; <code>null</code> if no command matches.
	 */
	private ParameterizedCommand getPerfectMatch(KeySequence keySequence) {
		Binding perfectMatch = getBindingService().getPerfectMatch(keySequence);
		return perfectMatch == null ? null : perfectMatch.getParameterizedCommand();
	}

