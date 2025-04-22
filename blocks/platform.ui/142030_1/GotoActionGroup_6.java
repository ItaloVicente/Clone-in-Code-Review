		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		if (selection.size() == 1) {
			if (ResourceSelectionUtil.allResourcesAreOfType(selection, IResource.FOLDER)) {
				menu.add(goIntoAction);
			} else {
				IStructuredSelection resourceSelection = ResourceSelectionUtil.allResources(selection,
						IResource.PROJECT);
				if (resourceSelection != null && !resourceSelection.isEmpty()) {
					IProject project = (IProject) resourceSelection.getFirstElement();
					if (project.isOpen()) {
