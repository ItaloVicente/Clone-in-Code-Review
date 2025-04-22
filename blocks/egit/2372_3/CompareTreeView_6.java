
			if (!(sourceObject instanceof PlatformObject)) {
				Object result = Platform.getAdapterManager().getAdapter(
						sourceObject, adapterType);
				if (result != null)
					return result;
			}
			return null;
		}
	}

	private final static class PathNodeTreeComparator extends ViewerComparator {
		private static final int UNKNOWNCATEGORY = 50;

		@Override
		public int category(Object element) {
			if (element instanceof PathNode) {
				return ((PathNode) element).type.ordinal();
			}
			return UNKNOWNCATEGORY;
