		return contentDescription;
	}

	protected void setContentDescription(String description) {
		internalSetContentDescription(description);

		setDefaultTitle();
	}

	void internalSetContentDescription(String description) {
		Assert.isNotNull(description);

		if (Objects.equals(contentDescription, description)) {
