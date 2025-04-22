		ElementMatcher matcher = new ElementMatcher(id, clazz, tagsToMatch);
		return findElements(searchRoot, matcher, searchFlags);
	}

	public <T> List<T> findElements(MApplicationElement searchRoot, Selector matcher,
			int searchFlags) {
