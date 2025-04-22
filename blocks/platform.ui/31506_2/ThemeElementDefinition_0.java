			return RESOURCE_BUNDLE.getString("Added.by.css.label").trim(); //$NON-NLS-1$
		}
		if (isOverridden() && isModifiedByUser()) {
			return MessageFormat.format(
					RESOURCE_BUNDLE.getString("Overridden.by.css.and.modified.by.user.label"), //$NON-NLS-1$
					new Object[] { description }).trim();
