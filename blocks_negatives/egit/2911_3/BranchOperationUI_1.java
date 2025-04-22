		if (target == null) {
			BranchSelectionDialog dialog = new BranchSelectionDialog(
					getShell(), repository);
			if (dialog.open() != Window.OK) {
				return;
			}
			target = dialog.getRefName();
		}
