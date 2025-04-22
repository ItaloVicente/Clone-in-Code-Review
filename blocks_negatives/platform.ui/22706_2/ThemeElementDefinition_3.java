		return overridden;
	}

	protected void setOverridden(boolean overridden) {
		this.overridden = overridden;
		if (isAddedByCss()) {
			return;
		}

		boolean hasOverriddenLabel = description.endsWith(getOverriddenLabel());
		if (overridden && !hasOverriddenLabel) {
			description += ' ' + getOverriddenLabel();
		} else if (!overridden && hasOverriddenLabel) {
			description = description.substring(0, description.length()
					- getOverriddenLabel().length() - 1);
		}
