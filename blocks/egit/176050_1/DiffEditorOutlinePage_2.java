	private void updateToolbarActions() {
		TreeViewer viewer = getTreeViewerChecked();
		if (viewer == null) {
			return;
		}
		boolean hasElements = ((DiffContentProvider) viewer
				.getContentProvider()).hasElements();
		if (collapseAction != null) {
			collapseAction.setEnabled(hasElements);
		}
		if (togglePresentationAction != null) {
			togglePresentationAction.setEnabled(hasElements);
		}
	}

