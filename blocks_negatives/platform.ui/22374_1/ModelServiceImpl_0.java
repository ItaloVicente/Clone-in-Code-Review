	/**
	 * Determine if the element passes the matching test for all non-null parameters.
	 *
	 * @param element
	 *            The element to test
	 * @param id
	 *            The Id
	 * @param clazz
	 *            The class that element must be an instance of
	 * @param tagsToMatch
	 *            The tags to check, <b>all</b> the specified rags must be in the element's tags
	 * @return <code>true</code> iff all the tests pass
	 */
	private boolean match(MUIElement element, String id, Class clazz, List<String> tagsToMatch) {
		if (id != null && !id.equals(element.getElementId())) {
			return false;
		}

		if (clazz != null && !(clazz.isInstance(element))) {
			return false;
		}

		if (tagsToMatch != null) {
			List<String> elementTags = element.getTags();
			for (String tag : tagsToMatch) {
				if (!elementTags.contains(tag)) {
					return false;
				}
			}
		}

		return true;
	}

	private <T> void findElementsRecursive(MUIElement searchRoot, String id,
			Class<? extends T> type, List<String> tagsToMatch, List<T> elements, int searchFlags) {
