	private String getTextFromLabelProvider(IBaseLabelProvider baseLabelProvider, Object element) {
		if (baseLabelProvider == null) {
			return null;
		}
		String labelText = null;
		if (baseLabelProvider instanceof ILabelProvider) {
			labelText = ((ILabelProvider) baseLabelProvider).getText(element);
		} else if (baseLabelProvider instanceof IStyledLabelProvider) {
			labelText = ((IStyledLabelProvider) baseLabelProvider).getStyledText(element).getString();
		} else if (baseLabelProvider instanceof DelegatingStyledCellLabelProvider) {
			IStyledLabelProvider styledStringProvider = ((DelegatingStyledCellLabelProvider) baseLabelProvider)
					.getStyledStringProvider();
			StyledString styledText = styledStringProvider.getStyledText(element);
			if (styledText != null) {
				labelText = styledText.getString();
			}
		}

		return labelText;
	}

