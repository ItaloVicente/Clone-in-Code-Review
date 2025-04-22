	private org.eclipse.e4.ui.workbench.modeling.ISelectionListener postListener = new org.eclipse.e4.ui.workbench.modeling.ISelectionListener() {
		@Override
		public void selectionChanged(MPart part, Object selection) {
			handlePostSelectionChanged(part, selection, false);
		}
	};
