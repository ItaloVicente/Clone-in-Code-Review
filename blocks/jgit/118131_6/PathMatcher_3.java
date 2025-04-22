		int slashIdx = segment.indexOf(Strings.getPathSeparator(pathSeparator)
				0);
		if (!pathMatch && (slashIdx < 0 || slashIdx >= segment.length() - 1)) {
			while (segment.contains(WildMatcher.WILDMATCH)) {
				segment = segment.replace(WildMatcher.WILDMATCH
			}
		} else if (segment.startsWith(WildMatcher.WILDMATCH)
				|| segment.startsWith(WildMatcher.WILDMATCH2)) {
			final int start = segment.charAt(0) != '*' ? 1 : 0;
			for (int pos = start; pos < segment.length(); pos++) {
				if (segment.charAt(pos) != '*') {
					return null;
				}
			}
