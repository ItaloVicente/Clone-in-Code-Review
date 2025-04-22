	private static final class ContentProvider implements
			IStructuredContentProvider {

		public Object[] getElements(Object inputElement) {
			return ((List) inputElement).toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

