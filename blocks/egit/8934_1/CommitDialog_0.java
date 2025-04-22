	static class CommitFileContentProvider extends BaseWorkbenchContentProvider {
		@Override
		public Object[] getElements(Object element) {
			if (element instanceof Object[]) {
				return (Object[]) element;
			}
			if (element instanceof Collection) {
				return ((Collection) element).toArray();
			}
			return new Object[0];
		}

		public Object[] getChildren(Object parentElement) {
			return new Object[0];
		}

		public Object getParent(Object element) {
			return null;
		}

		public boolean hasChildren(Object element) {
			return false;
		}
	}

