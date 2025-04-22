				tagsChangeHandler);
	}

	private boolean isBusyTagModified(Object oldValue, Object newValue) {
		return (newValue == null && CSSConstants.CSS_BUSY_CLASS
				.equals(oldValue))
				|| (oldValue == null && CSSConstants.CSS_BUSY_CLASS
						.equals(newValue));
