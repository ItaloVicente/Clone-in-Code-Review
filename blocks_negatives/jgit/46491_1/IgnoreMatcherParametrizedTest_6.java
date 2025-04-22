		if (useOldRule.booleanValue()) {
			IgnoreRule r = new IgnoreRule(pattern);
			match = r.isMatch(target, isDirectory);
		} else {
			FastIgnoreRule r = new FastIgnoreRule(pattern);
			match = r.isMatch(target, isDirectory);
		}
