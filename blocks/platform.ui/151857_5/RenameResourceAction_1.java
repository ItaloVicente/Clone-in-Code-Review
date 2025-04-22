
		if (LTKLauncher.isCompositeRename(getStructuredSelection()) || this.navigatorTree == null) {
			if (!LTKLauncher.openRenameWizard(getStructuredSelection())) {
				if (!checkReadOnlyAndNull(currentResource)) {
					return;
				}
				String newName = queryNewResourceName(currentResource);
				if (newName == null || newName.isEmpty()) {
					return;
				}
				newPath = currentResource.getFullPath().removeLastSegments(1).append(newName);
				super.run();
