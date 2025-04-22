		state.set(newState(fromTextRecurse(text
	}

	private List<ConfigLine> fromTextRecurse(final String text
			throws ConfigInvalidException {
		if (depth > MAX_DEPTH) {
			throw new ConfigInvalidException(
					JGitText.get().tooManyIncludeRecursions);
		}
