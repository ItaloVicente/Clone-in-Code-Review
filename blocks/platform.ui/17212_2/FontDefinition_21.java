		if (parsedValue == null) {
			parsedValue = JFaceResources.getFontRegistry().filterData(
					StringConverter.asFontDataArray(value), PlatformUI.getWorkbench().getDisplay());
		}

		return parsedValue;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public boolean equals(Object obj) {
		if (obj instanceof FontDefinition) {
			return getId().equals(((FontDefinition) obj).getId());
		}
		return false;
	}

	public int hashCode() {
		return id.hashCode();
	}

	public void setValue(FontData[] data) {
		if (data != null && data.length > 0) {
			value = data[0].getName();
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
