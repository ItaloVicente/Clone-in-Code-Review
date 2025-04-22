			if (isWildcard(s)) {
				if (!allowMismatchedWildcards) {
					throw new IllegalArgumentException(MessageFormat.format(JGitText.get().invalidWildcards
				}
				wildcard = true;
			}
