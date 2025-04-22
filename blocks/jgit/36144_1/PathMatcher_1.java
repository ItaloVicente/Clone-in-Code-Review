		if (isWildCard(segment)) {
			if (hasLeadingAsteriskOnly(segment))
				return new LeadingAsteriskMatcher(segment
						dirOnly);
			else if (hasTrailingAsteriskOnly(segment))
				return new TrailingAsteriskMatcher(segment
						dirOnly);
			else
				return new WildCardMatcher(segment
		}
