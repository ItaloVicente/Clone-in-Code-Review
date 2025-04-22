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
