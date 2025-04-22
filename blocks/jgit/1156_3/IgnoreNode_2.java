	public MatchResult isIgnored(String entryPath
		if (rules.isEmpty())
			return MatchResult.CHECK_PARENT;

		for (int i = rules.size() - 1; i > -1; i--) {
			IgnoreRule rule = rules.get(i);
			if (rule.isMatch(entryPath
				if (rule.getResult())
					return MatchResult.IGNORED;
				else
					return MatchResult.NOT_IGNORED;
			}
		}
		return MatchResult.CHECK_PARENT;
