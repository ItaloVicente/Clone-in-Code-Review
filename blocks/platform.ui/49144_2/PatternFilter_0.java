	private String getTextFromLabelProvider(IBaseLabelProvider baseLabelProvider, Object element) {
		String labelText = null;
		if (baseLabelProvider instanceof ILabelProvider) {
			labelText = ((ILabelProvider) baseLabelProvider).getText(element);
		} else if (baseLabelProvider instanceof IStyledLabelProvider) {
			labelText = ((IStyledLabelProvider) baseLabelProvider).getStyledText(element).getString();
		}

		return labelText;
	}

