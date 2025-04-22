		final IStructuredSelection selection = (IStructuredSelection) context.getSelection();
		if (ResourceSelectionUtil.allResourcesAreOfType(selection, IResource.PROJECT) == false) {
			return;
		}
		List sel = selection.toList();
		IResourceDelta delta = event.getDelta();
		if (delta == null) {
			return;
		}
