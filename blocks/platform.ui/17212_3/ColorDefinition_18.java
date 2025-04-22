
	public void setValue(RGB data) {
		if (data != null) {
			parsedValue = data;
			if (!isOverridden()) {
				description += ' ' + RESOURCE_BUNDLE.getString("Overridden.by.css.label"); //$NON-NLS-1$
				overridden = true;
			}
		}
	}

	public boolean isOverridden() {
		return overridden;
	}
