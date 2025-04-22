		if (useOldRule.booleanValue()) {
			IgnoreRule r = new IgnoreRule(pattern);
			boolean match = r.isMatch(target, isDirectory);
			if (r.getNegation())
				match = !match;
			return match;
		}
