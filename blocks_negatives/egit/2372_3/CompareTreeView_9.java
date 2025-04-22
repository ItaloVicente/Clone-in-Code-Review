		public Object[] getChildren(Object element) {
			boolean rebuildArray = false;
			Object[] children;
			if (element == input)
				children = (Object[]) input;
			else
				children = super.getChildren(element);
			List<Object> childList = new ArrayList<Object>(children.length);
			for (Object child : children) {
				IPath path = new Path(repositoryMapping
						.getRepoRelativePath((IResource) child));
				if (child instanceof IFile && (leftOnly.contains(path))) {
					rebuildArray = true;
					continue;
				}
				if (child instanceof IFile && showDeletedOnly
						&& !rightOnly.contains(path)) {
					rebuildArray = true;
					continue;
				}
				if (child instanceof IContainer
						&& !rightPathsWithChildren.contains(path)) {
					rebuildArray = true;
					continue;
