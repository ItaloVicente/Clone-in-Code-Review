		String viewLabel = LocalizationHelper.getLocalized(viewDescriptor.getLabel(), viewDescriptor,
				window.getContext());
		String categoryLabel = LocalizationHelper.getLocalized(viewDescriptor.getCategory(), viewDescriptor,
				window.getContext());
		if (categoryLabel != null) {
			return NLS.bind(QuickAccessMessages.QuickAccess_ViewWithCategory, viewLabel, categoryLabel);
		}
		return viewLabel;
