		final Boolean result = checkIgnored(entryPath
		if (result == null) {
			return negateFirstMatch
					? MatchResult.CHECK_PARENT_NEGATE_FIRST_MATCH
					: MatchResult.CHECK_PARENT;
		}

		return result ? MatchResult.IGNORED : MatchResult.NOT_IGNORED;
	}
