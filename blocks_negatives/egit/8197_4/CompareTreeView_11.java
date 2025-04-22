		@Override
		public int category(Object element) {
			IResource adapter = (IResource) getAdapter(element, IResource.class);
			if (adapter != null) {
				if (adapter instanceof IContainer) {
					return FOLDERCATEGORY;
				}
				if (adapter instanceof IFile) {
					IFile file = (IFile) adapter;
					IPath path = new Path(repositoryMapping
							.getRepoRelativePath(file));
					if (addedPaths.contains(path))
						return ADDEDCATEGORY;
					if (equalContentPaths.contains(path))
						return UNCHANGEDCATEGORY;
					return CHANGEDCATEGORY;
				}
			}
			if (element instanceof PathNodeAdapter)
				return DELETEDCATEGORY;
