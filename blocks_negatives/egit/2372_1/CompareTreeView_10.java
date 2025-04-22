			IResource[] resources = (IResource[]) input;
			PathNode[] nodes = new PathNode[resources.length];
			for (int i = 0; i < resources.length; i++) {
				IResource resource = resources[i];
				if (resource instanceof IFile) {
					IPath path = new Path(repositoryMapping
							.getRepoRelativePath(resource));
					Type type;
					if (leftOnly.contains(path))
						type = Type.FILE_ADDED;
					else if (equalIds.contains(path))
						type = Type.FILE_BOTH_SIDES_SAME;
					else
						type = Type.FILE_BOTH_SIDES_DIFFER;
					nodes[i] = new PathNode(path, type);
				} else
					nodes[i] = new PathNode(new Path(repositoryMapping
							.getRepoRelativePath(resource)), Type.FOLDER);
			}
			return nodes;
		}

		public Object getParent(Object element) {
			if (!(element instanceof PathNode))
				return null;
			IPath currentPath = ((PathNode) element).path;
			if (currentPath.segmentCount() > 0)
				return currentPath.removeLastSegments(1);
			return null;
		}
	}

	/**
	 * Used to render the left tree in case we have no workspace
	 */
	private final class LeftTreeContentProvider extends
			AbstractPathNodeContentProvider implements ITreeContentProvider {
		@Override
		public Object[] getElements(Object inputElement) {
			if (leftPathsWithChildren.isEmpty() && leftOnly.isEmpty())
