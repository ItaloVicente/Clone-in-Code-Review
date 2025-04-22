			if (isWildcard(s)) {
				if (allowMissmatchedWildcards) {
					wildcard = true;
				} else {
					throw new IllegalArgumentException(MessageFormat.format(JGitText.get().invalidWildcards
				}
			}
