			if (isWildcard(s)) {
				if (allowMismatchedWildcards) {
					wildcard = true;
				} else {
					throw new IllegalArgumentException(MessageFormat.format(JGitText.get().invalidWildcards
				}
			}
