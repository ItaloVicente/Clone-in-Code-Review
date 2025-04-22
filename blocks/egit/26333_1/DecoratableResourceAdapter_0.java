	private static boolean hasContainerAnyFiles(IResource resource) {
		if (resource instanceof IContainer) {
			IContainer container = (IContainer) resource;
			try {
				return anyFile(container.members());
			} catch (CoreException e) {
				return true;
			}
		}
		throw new IllegalArgumentException("Expected a container resource."); //$NON-NLS-1$
	}

	private static boolean anyFile(IResource[] memebers) {
		for (IResource member : memebers) {
			if (member.getType() == IResource.FILE)
				return true;
			else if (member.getType() == IResource.FOLDER)
				return hasContainerAnyFiles(member);
		}
		return false;
	}

