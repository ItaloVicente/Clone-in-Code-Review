
	private final static class PathNodeAdapter implements IWorkbenchAdapter {
		private final static Object[] EMPTYARRAY = new Object[0];

		PathNode pathNode;

		public PathNodeAdapter(PathNode path) {
			pathNode = path;
		}

		public Object[] getChildren(Object o) {
			return EMPTYARRAY;
		}

		public ImageDescriptor getImageDescriptor(Object object) {
			return UIIcons.ELCL16_DELETE;
		}

		public String getLabel(Object o) {
			return pathNode.path.lastSegment();
		}

		public Object getParent(Object o) {
			return null;
		}
	}
