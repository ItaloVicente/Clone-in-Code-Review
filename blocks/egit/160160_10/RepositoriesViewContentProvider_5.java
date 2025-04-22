	@FunctionalInterface
	private interface Matcher {
		boolean matches(String s);
	}

	private Object[] getTagsChildren(TagsNode parentNode,
