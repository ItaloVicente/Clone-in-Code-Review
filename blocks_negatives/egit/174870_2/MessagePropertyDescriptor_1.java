	@Override
	public String getDescription() {
		String description = super.getDescription();
		if (description == null) {
			description = getDisplayName();
		}
		return description;
	}

