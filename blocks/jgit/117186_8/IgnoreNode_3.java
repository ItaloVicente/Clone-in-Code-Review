		final Boolean result = checkIgnored(entryPath
		if (result == null) {
			return MatchResult.CHECK_PARENT;
		}

		return result ? MatchResult.IGNORED : MatchResult.NOT_IGNORED;
