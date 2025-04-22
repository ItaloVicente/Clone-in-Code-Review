		if (resource instanceof IContainer) {
			IResource[] children;
			try {
				children = ((IContainer) resource).members();
			} catch (CoreException exception) {
				return wrappers;
			}
			wrappers = new AdaptableResourceWrapper[children.length];
			for (int i = 0; i < children.length; i++) {
				wrappers[i] = new AdaptableResourceWrapper(children[i]);
			}
		}
		return wrappers;
	}
