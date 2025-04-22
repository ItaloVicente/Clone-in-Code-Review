		public String getToolTipText(Object element) {
			return ((CommitItem) element).status.getText();
		}

		public void dispose() {
			resourceManager.dispose();
			super.dispose();
