		if (commitId == null && refName == null) {
			BranchSelectionDialog dialog = new BranchSelectionDialog(
					getShell(), repository);
			if (dialog.open() != Window.OK) {
				return;
			}
			refName = dialog.getRefName();
		}
