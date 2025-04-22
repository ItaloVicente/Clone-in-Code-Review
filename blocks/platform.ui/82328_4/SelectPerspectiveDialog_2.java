    private void updateTooltip() {
		String tooltip = ""; //$NON-NLS-1$
		if (perspDesc != null) {
			tooltip = perspDesc.getDescription();
		}

		boolean hasTooltip = tooltip != null && tooltip.length() > 0;
		descriptionHint.setVisible(hasTooltip);

		if (perspDescPopupDialog != null) {
			perspDescPopupDialog.close();
			perspDescPopupDialog = null;
		}
	}

	@Override
