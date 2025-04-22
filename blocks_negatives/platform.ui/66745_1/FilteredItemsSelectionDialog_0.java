	/**
	 * A content provider that does nothing.
	 */
	private class NullContentProvider implements IContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

