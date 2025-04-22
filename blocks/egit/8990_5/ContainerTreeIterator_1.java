		if (FileMode.TREE.equals(mode)) {
			if (current() instanceof ResourceEntry) {
				ResourceEntry resourceEntry = (ResourceEntry) current();
				return new ContainerTreeIterator(this,
						(IContainer) resourceEntry.rsrc,
						resourceEntry.hasInheritedResourceFilters);
			} else if (current() instanceof FileEntry) {
				FileEntry fileEntry = (FileEntry) current();
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				return new AdaptableFileTreeIterator(this, fileEntry.getFile(), root);
			} else {
				throw new IllegalStateException("Unknown entry type: " + current()); //$NON-NLS-1$
			}
		} else
