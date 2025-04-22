		updateSelection(event);
		updateButtons();
		String tooltip = ""; //$NON-NLS-1$
		if (viewDescs.length > 0) {
			tooltip = viewDescs[0].getTooltip();
			tooltip = LocalizationHelper.getLocalized(tooltip, viewDescs[0], context);
		}
		boolean hasTooltip = (tooltip == null) ? false : tooltip.length() > 0;
		descriptionHint.setVisible(viewDescs.length == 1 && hasTooltip);
