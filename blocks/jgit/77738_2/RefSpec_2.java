			if (isWildcard(s)) {
				if (allowMissmatchedWildcards) {
					wildcard = true;
					dstName = checkValid(s);
				} else {
					throw new IllegalArgumentException(MessageFormat.format(JGitText.get().invalidWildcards
				}
			}
