
	static final class NoResultMatcher implements IMatcher {

		public boolean matches(String path, boolean assumeDirectory) {
			return false;
		}

		public boolean matches(String segment, int startIncl, int endExcl,
				boolean assumeDirectory) {
			return false;
		}
	}
