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
