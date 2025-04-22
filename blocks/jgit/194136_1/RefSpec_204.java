				srcName = Constants.R_HEADS + '*';
				dstName = srcName;
			} else {
				if (isWildcard(s)) {
					wildcard = true;
					if (mode == WildcardMode.REQUIRE_MATCH) {
						throw new IllegalArgumentException(MessageFormat
								.format(JGitText.get().invalidWildcards
					}
