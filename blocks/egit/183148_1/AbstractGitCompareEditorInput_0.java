				&& !isMultiFile()) {
			ITypedElement element = getElement();
			IResource resource = getResource(element);
			if (resource != null && adapter.isInstance(resource)
					&& resource.exists()) {
				return resource;
			}
		} else if (adapter == IShowInSource.class && isMultiFile()) {
			DiffNode node = getNode();
			if (node instanceof FolderNode) {
				FolderNode folder = (FolderNode) node;
				IContainer container = folder.getContainer();
				if (container != null) {
					return getShowInSource(new ShowInContext(this,
							new StructuredSelection(container)));
				}
				IPath path = folder.getPath();
				if (path != null) {
					return getShowInSource(new ShowInContext(this,
							new StructuredSelection(path)));
