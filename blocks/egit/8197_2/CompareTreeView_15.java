	private final class PathNodeFilter extends ViewerFilter {
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (showEquals)
				return true;
			else {
				if (element instanceof FileNode) {
					FileNode fileNode = (FileNode) element;
					return fileNode.getType() != Type.FILE_BOTH_SIDES_SAME;
				} else if (element instanceof ContainerNode) {
					ContainerNode containerNode = (ContainerNode) element;
					return !containerNode.isOnlyEqualContent();
				} else if (element instanceof String)
					return true;
			}
			return true;
