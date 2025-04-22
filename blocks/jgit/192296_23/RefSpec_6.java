
		if (isNegative()) {
			if (isNullOrEmpty(srcName) && isNullOrEmpty(dstName)) {
				throw new IllegalArgumentException(MessageFormat
						.format(JGitText.get().invalidRefSpec
			}
			if (!isNullOrEmpty(srcName) && !isNullOrEmpty(dstName)) {
				throw new IllegalArgumentException(MessageFormat
						.format(JGitText.get().invalidRefSpec
			}
			if(wildcard && mode == WildcardMode.REQUIRE_MATCH) {
				throw new IllegalArgumentException(MessageFormat
						.format(JGitText.get().invalidRefSpec
		}
