		if (formattedDescription == null || stateDuringFormattingMessage != state) {
			formattedDescription = formatDescription();
			stateDuringFormattingMessage = state;
		}
		return formattedDescription;
	}

	private String formatDescription() {
		String description = this.description != null ? this.description : label;
		String modifiedByUserLabel = isModifiedByUser() ? RESOURCE_BUNDLE
				.getString("Modified.by.user.label") : ""; //$NON-NLS-1$ //$NON-NLS-2$
		if (isAddedByCss()) {
			return RESOURCE_BUNDLE.getString("Added.by.css.desc").trim(); //$NON-NLS-1$
		}
		if (isOverridden()) {
			return MessageFormat.format(RESOURCE_BUNDLE.getString("Overridden.by.css.label"), //$NON-NLS-1$
					new Object[] { description, modifiedByUserLabel }).trim();
		}
