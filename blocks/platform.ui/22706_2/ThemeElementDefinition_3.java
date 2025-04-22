		if (formattedDescription == null) {
			formattedDescription = isOverridden()? description + ' ' + getOverriddenLabel() : description;			
		} else if (!isOverridden() && formattedDescription.endsWith(getOverriddenLabel())) {
			formattedDescription = formattedDescription.substring(0, formattedDescription.length()
					- getOverriddenLabel().length() - 1);
		}
		return formattedDescription;
