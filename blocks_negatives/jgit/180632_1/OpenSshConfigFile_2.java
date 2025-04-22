	private static boolean isHostMatch(String pattern, String name) {
			return !patternMatchesHost(pattern.substring(1), name);
		}
		return patternMatchesHost(pattern, name);
	}

