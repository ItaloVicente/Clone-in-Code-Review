	private boolean hasContainerAnyFiles(IResource resource) {
		if (resource instanceof IContainer) {
			IContainer container = (IContainer) resource;
			try {
				return anyFile(container.members());
			} catch (CoreException e) {
				return true;
			}
		}
	}
