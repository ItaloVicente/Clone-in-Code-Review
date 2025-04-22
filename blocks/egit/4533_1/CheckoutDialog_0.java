		if (((TreeSelection) branchTree.getSelection()).size() > 1) {
			TreeSelection selection = (TreeSelection) branchTree
					.getSelection();
			boolean onlySelectionAreBranches = onlyBranchesAreSelected(selection);

			deleteteButton.setEnabled(onlySelectionAreBranches);
			renameButton.setEnabled(!onlySelectionAreBranches);
			newButton.setEnabled(!onlySelectionAreBranches);
		} else {
			getButton(Window.OK).setEnabled(branchSelected || tagSelected);

			renameButton.setEnabled(branchSelected && !tagSelected);
			deleteteButton.setEnabled(branchSelected && !tagSelected);

			newButton.setEnabled(true);
		}
