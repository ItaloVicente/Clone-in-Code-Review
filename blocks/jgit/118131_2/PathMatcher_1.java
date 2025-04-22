		if (segment.startsWith(WildMatcher.WILDMATCH)
				|| segment.startsWith(WildMatcher.WILDMATCH2)) {
			while (segment.contains(WildMatcher.WILDMATCH_DEGENERATED)) {
				segment = segment.replace(WildMatcher.WILDMATCH_DEGENERATED
						WildMatcher.WILDMATCH);
			}

			if (WildMatcher.WILDMATCH.equals(segment)
					|| WildMatcher.WILDMATCH2.equals(segment)) {
				return dirOnly && lastSegment ? WILD_ONLY_DIRECTORY
						: WILD_NO_DIRECTORY;
			}
		}
