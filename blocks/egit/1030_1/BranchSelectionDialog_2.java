
	@Override
	protected void refNameSelected(String refName) {
		boolean tagSelected = refName != null
				&& refName.startsWith(Constants.R_TAGS);

		boolean branchSelected = refName != null
				&& (refName.startsWith(Constants.R_HEADS) || refName
						.startsWith(Constants.R_REMOTES));

		getButton(Window.OK).setEnabled(branchSelected || tagSelected);

		if (renameButton != null) {
			renameButton.setEnabled(branchSelected && !tagSelected);
		}

		if (newButton != null) {
			newButton.setEnabled(branchSelected && !tagSelected);
		}
	}
