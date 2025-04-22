
		if (isNegative()) {
			if (srcName == null && dstName == null) {
				throw new IllegalArgumentException(MessageFormat
						.format(JGitText.get().invalidRefSpec
			}
			if (srcName != null && dstName != null) {
				throw new IllegalArgumentException(MessageFormat
						.format(JGitText.get().invalidRefSpec
			}
			if(wildcard && mode == WildcardMode.REQUIRE_MATCH) {
				throw new IllegalArgumentException(MessageFormat
						.format(JGitText.get().invalidRefSpec
		}
