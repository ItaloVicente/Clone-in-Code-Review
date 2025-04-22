		public String getToolTipText(Object element) {
			return ((CommitItem) element).status.getText();
		}

		public void dispose() {
			resourceManager.dispose();
			super.dispose();
		}

	}

	static class CommitPathLabelProvider extends ColumnLabelProvider {

		public String getText(Object obj) {
			return ((CommitItem) obj).path;
		}

		public String getToolTipText(Object element) {
			return ((CommitItem) element).status.getText();
