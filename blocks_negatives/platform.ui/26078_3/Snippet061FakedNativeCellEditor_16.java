	private class MyContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return ((File) inputElement).child.toArray();
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return getElements(parentElement);
		}

		@Override
		public Object getParent(Object element) {
			if (element == null) {
				return null;
			}
			return ((File) element).parent;
		}

		@Override
		public boolean hasChildren(Object element) {
			return ((File) element).child.size() > 0;
		}

	}

