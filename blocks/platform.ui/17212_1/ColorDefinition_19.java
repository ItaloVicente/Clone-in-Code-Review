
	public void setValue(RGB data) {
		if (data != null) {
			parsedValue = data;
			if (!isOverriden()) {
				description += ' ' + RESOURCE_BUNDLE.getString("Overriden.by.css.label"); //$NON-NLS-1$
				overriden = true;
			}
		}
	}

	public boolean isOverriden() {
		return overriden;
	}
