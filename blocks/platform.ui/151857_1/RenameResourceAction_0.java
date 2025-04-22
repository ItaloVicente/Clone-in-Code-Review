		if (LTKLauncher.isCompositeRename(getStructuredSelection())) {
			if (!LTKLauncher.openRenameWizard(getStructuredSelection())) {
				String newName = queryNewResourceName(currentResource);
				IPath newPath = currentResource.getFullPath().removeLastSegments(1).append(newName);
				runWithNewPath(newPath, currentResource);
			}
		} else {
