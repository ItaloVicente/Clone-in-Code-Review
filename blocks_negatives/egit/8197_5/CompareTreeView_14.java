	 * Used to render the "local" (workspace) side of a tree compare where one
	 * side is the workspace
	 */
	private final class WorkbenchTreeContentProvider extends
			WorkbenchContentProvider {
		@Override
		public Object[] getChildren(Object element) {
			boolean rebuildArray = false;
			Object[] children;
			if (element == input)
				children = (Object[]) input;
			else
				children = super.getChildren(element);
			List<Object> childList = new ArrayList<Object>(children.length);
			for (Object child : children) {
				IResource childResource = (IResource) child;
				if (childResource.isLinked())
					continue;
				IPath path = new Path(
						repositoryMapping.getRepoRelativePath(childResource));
				boolean isFile = childResource.getType() == IResource.FILE;

				if (isFile && !compareVersionMap.containsKey(path)
						&& !addedPaths.contains(path)) {
					rebuildArray = true;
					continue;
				}
				if (!showEquals && equalContentPaths.contains(path)) {
					rebuildArray = true;
					continue;
				}
				if (child instanceof IContainer
						&& !baseVersionPathsWithChildren.contains(path)) {
					rebuildArray = true;
					continue;
				}
				if (!showEquals && equalContentPaths.contains(path)) {
					rebuildArray = true;
					continue;
				}
				childList.add(child);
			}
			if (element instanceof IContainer) {
				List<PathNodeAdapter> deletedChildren = compareVersionPathsWithChildren
						.get(new Path(repositoryMapping
								.getRepoRelativePath((IResource) element)));
				if (deletedChildren != null) {
					rebuildArray = true;
					for (IWorkbenchAdapter path : deletedChildren)
						childList.add(path);
				}
			}
			if (rebuildArray)
				return childList.toArray();
			return children;
		}
	}

	/**
	 * Used to render the tree in case we have no workspace
