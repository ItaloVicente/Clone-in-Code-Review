		private IMatcher createPattern(String pattern) {
			try {
				return PathMatcher.createPathMatcher(pattern,
						Character.valueOf('/'), false);
			} catch (InvalidPatternException e) {
				return null;
			}
