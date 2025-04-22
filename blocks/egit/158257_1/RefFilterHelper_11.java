		@Nullable
		private IMatcher createPattern(@Nullable String pattern) {
			if (pattern == null) {
				return null;
			}
			try {
				return IMatcher.createPathMatcher(pattern, false);
			} catch (InvalidPatternException e) {
				return null;
			}
