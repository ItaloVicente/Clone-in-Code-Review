	@Override
	public <T> List<T> findElements(MUIElement searchRoot, String id, Class<T> clazz,
			List<String> tagsToMatch) {
		ElementMatcher matcher = new ElementMatcher(id, clazz, tagsToMatch);
		return findElements(searchRoot, clazz, ANYWHERE, matcher);
	}
