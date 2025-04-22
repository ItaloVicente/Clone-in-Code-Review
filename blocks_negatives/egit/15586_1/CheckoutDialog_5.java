	@Override
	protected String getWindowTitle() {
		return UIText.CheckoutDialog_WindowTitle;
	}

	@Override
	protected void refNameSelected(String refName) {
		boolean tagSelected = refName != null
				&& refName.startsWith(Constants.R_TAGS);

		boolean branchSelected = refName != null
				&& (refName.startsWith(Constants.R_HEADS) || refName
						.startsWith(Constants.R_REMOTES));

		if (((TreeSelection) branchTree.getSelection()).size() > 1) {
			TreeSelection selection = (TreeSelection) branchTree
					.getSelection();
			boolean onlyBranchesAreSelected = onlyBranchesAreSelected(selection);

			deleteteButton.setEnabled(onlyBranchesAreSelected);
			renameButton.setEnabled(false);
			newButton.setEnabled(false);
		} else {
			getButton(Window.OK).setEnabled(branchSelected || tagSelected);

			renameButton.setEnabled(branchSelected && !tagSelected);
			deleteteButton.setEnabled(branchSelected && !tagSelected);

			newButton.setEnabled(true);
		}

		getButton(Window.OK).setEnabled(
				refName != null && !refName.equals(currentBranch));
	}

