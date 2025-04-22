		private static final Object[] EMPTY = new Object[0];

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement == null || !inputElement.getClass().isArray()) {
				return EMPTY;
			}
			Object[] orig = (Object[]) inputElement;
			Object[] arr = new Object[orig.length];
			System.arraycopy(orig, 0, arr, 0, arr.length);
			return arr;
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return EMPTY;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return false;
		}

	}

	private IEditorDescriptor selectedEditor;

	private IEditorDescriptor hiddenSelectedEditor;
