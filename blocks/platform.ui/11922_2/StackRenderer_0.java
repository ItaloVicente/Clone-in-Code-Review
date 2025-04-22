	private boolean isAnyOfTagsModified(Object oldValue, Object newValue,
			String... tagNames) {
		for (String tagName : tagNames) {
			if ((newValue == null && tagName.equals(oldValue))
					|| (oldValue == null && tagName.equals(newValue))) {
				return true;
			}
		}
		return false;
