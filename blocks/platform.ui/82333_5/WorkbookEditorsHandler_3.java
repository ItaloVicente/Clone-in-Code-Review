	protected ViewerFilter getFilter() {
		return new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				SearchPattern matcher = getMatcher();
				if (matcher == null || !(viewer instanceof TableViewer)) {
					return true;
				}
				String matchName = null;
				if (element instanceof EditorReference) {
					matchName = ((EditorReference) element).getTitle();
				}
				if (matchName == null) {
					return false;
