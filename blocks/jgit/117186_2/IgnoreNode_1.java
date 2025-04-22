		while (entryPath != null) {
			for (int i = rules.size() - 1; i > -1; i--) {
				FastIgnoreRule rule = rules.get(i);
				if (rule.isMatch(entryPath
					if (rule.getResult()) {
						if (negateFirstMatch)
							negateFirstMatch = false;
						else
							return MatchResult.IGNORED;
					} else {
						if (negateFirstMatch)
							return MatchResult.NOT_IGNORED;
						else
							negateFirstMatch = true;
					}
