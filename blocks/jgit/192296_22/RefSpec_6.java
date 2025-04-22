
		if (isNegative()) {
			if (isNullOrEmptry(srcName) && isNullOrEmptry(dstName)) {
				throw new IllegalArgumentException(MessageFormat
						.format(JGitText.get().invalidRefSpec
			}
			if (!isNullOrEmptry(srcName) && !isNullOrEmptry(dstName)) {
				throw new IllegalArgumentException(MessageFormat
						.format(JGitText.get().invalidRefSpec
			}
			if(wildcard && mode == WildcardMode.REQUIRE_MATCH) {
				throw new IllegalArgumentException(MessageFormat
						.format(JGitText.get().invalidRefSpec
		}
