	protected void doInsert(TestModelChange change) {
		if (fViewer instanceof ListViewer) {
			if (change.getParent() != null
					&& change.getParent().equals(fViewer.getInput())) {
				((ListViewer) fViewer).add(change.getChildren());
			}
		} else if (fViewer instanceof TableViewer) {
			if (change.getParent() != null
					&& change.getParent().equals(fViewer.getInput())) {
				((TableViewer) fViewer).add(change.getChildren());
			}
		} else if (fViewer instanceof AbstractTreeViewer) {
			((AbstractTreeViewer) fViewer).add(change.getParent(), change
					.getChildren());
		} else if (fViewer instanceof ComboViewer) {
			((ComboViewer) fViewer).add(change.getChildren());
		} else {
			Assert.isTrue(false, "Unknown kind of viewer");
		}
	}
