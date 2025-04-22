
		if (initialBranch.getCombo().getSelectionIndex() < 0) {
			setErrorMessage(UIText.CloneDestinationPage_errorInitialBranchRequired);
			setPageComplete(false);
			return;
		}

