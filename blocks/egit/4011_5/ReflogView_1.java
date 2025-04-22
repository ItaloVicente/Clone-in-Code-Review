		if (!(selection instanceof IStructuredSelection))
			return;
		IStructuredSelection ssel = (IStructuredSelection) selection;
		if (ssel.size() != 1)
			return;
		Repository selectedRepo = null;
		Object first = ssel.getFirstElement();
		if (first instanceof IResource) {
			IResource resource = (IResource) ssel.getFirstElement();
			RepositoryMapping mapping = RepositoryMapping.getMapping(resource
					.getProject());
			if (mapping != null)
				selectedRepo = mapping.getRepository();
		}
		if (first instanceof IAdaptable) {
			IResource adapted = (IResource) ((IAdaptable) ssel
					.getFirstElement()).getAdapter(IResource.class);
			if (adapted != null) {
