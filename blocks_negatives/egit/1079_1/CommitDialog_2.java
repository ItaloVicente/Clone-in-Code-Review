	class CommitContentProvider implements IStructuredContentProvider {

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object inputElement) {
			return items.toArray();
		}

	}

