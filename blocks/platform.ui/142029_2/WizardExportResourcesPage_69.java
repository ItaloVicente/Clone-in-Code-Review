		BusyIndicator.showWhile(getShell().getDisplay(), runnable);

	}

	private void setupSelectionsBasedOnSelectedTypes(Map selectionMap, IContainer parent) {

		List selections = new ArrayList();
		IResource[] resources;
		boolean hasFiles = false;

		try {
			resources = parent.members();
		} catch (CoreException exception) {
			return;
		}

		for (IResource resource : resources) {
			if (resource.getType() == IResource.FILE) {
				if (hasExportableExtension(resource.getName())) {
					hasFiles = true;
					selections.add(resource);
				}
			} else {
				setupSelectionsBasedOnSelectedTypes(selectionMap, (IContainer) resource);
			}
		}

		if (hasFiles) {
