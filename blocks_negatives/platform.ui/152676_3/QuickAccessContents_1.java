		Set<String> prevPickIds = new HashSet<>();
		do {
			done = true;
			Matcher categoryMatcher = getCategoryPattern().matcher(filter);
			if (categoryMatcher.matches()) {
				category = categoryMatcher.group(1);
