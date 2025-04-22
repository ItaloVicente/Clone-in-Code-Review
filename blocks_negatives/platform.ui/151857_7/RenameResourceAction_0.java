		if (LTKLauncher.openRenameWizard(getStructuredSelection())) {
			return;
		}
		if (this.navigatorTree == null) {
			if (!checkReadOnlyAndNull(currentResource)) {
				return;
			}
			String newName = queryNewResourceName(currentResource);
			if (newName == null || newName.isEmpty()) {
				return;
