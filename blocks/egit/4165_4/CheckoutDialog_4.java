		boolean tagSelected = refName != null
				&& refName.startsWith(Constants.R_TAGS);

		boolean branchSelected = refName != null
				&& (refName.startsWith(Constants.R_HEADS) || refName
						.startsWith(Constants.R_REMOTES));

		getButton(Window.OK).setEnabled(branchSelected || tagSelected);

		renameButton.setEnabled(branchSelected && !tagSelected);
		deleteteButton.setEnabled(branchSelected && !tagSelected);

