	}

	public void setValue(FontData[] data) {
		if (data != null && data.length > 0) {
			value = data[0].getName();
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
