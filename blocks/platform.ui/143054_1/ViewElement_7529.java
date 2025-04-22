			viewLabel = NLS.bind(QuickAccessMessages.QuickAccess_ViewWithCategory, viewLabel, categoryLabel);
		}
		String description = LocalizationHelper.getLocalized(viewDescriptor.getTooltip(), viewDescriptor,
				window.getContext());
		if (description != null && !description.isEmpty()) {
			return viewLabel + separator + description;
