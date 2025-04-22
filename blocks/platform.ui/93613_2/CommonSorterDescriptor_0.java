	private ViewerSorter createSorterInstance() throws CoreException {
		Object contributed = element.createExecutableExtension(ATT_CLASS);
		if (contributed instanceof ViewerSorter) {
			return (ViewerSorter) contributed;
		}
		if (contributed instanceof ViewerComparator) {
			return new WrappedViewerComparator((ViewerComparator) contributed);
		}
		throw new ClassCastException("Class contributed by " + element.getNamespaceIdentifier() + //$NON-NLS-1$
				" to " + INavigatorContentExtPtConstants.TAG_NAVIGATOR_CONTENT + //$NON-NLS-1$
				"/" + INavigatorContentExtPtConstants.TAG_COMMON_SORTER //$NON-NLS-1$
				+ " is not an instance of " + ViewerComparator.class.getName() + ": " + contributed.getClass().getName() //$NON-NLS-1$ //$NON-NLS-2$
		);
	}

	public static class WrappedViewerComparator extends ViewerSorter {

		private final ViewerComparator comparator;

		public WrappedViewerComparator(ViewerComparator comparator) {
			this.comparator = comparator;
		}

		@Override
		public int category(Object element) {
			return comparator.category(element);
		}

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			return comparator.compare(viewer, e1, e2);
		}

		@Override
		public boolean isSorterProperty(Object element, String property) {
			return comparator.isSorterProperty(element, property);
		}

		public ViewerComparator getWrappedComparator() {
			return comparator;
		}
	}

