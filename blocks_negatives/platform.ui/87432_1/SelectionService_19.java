	private org.eclipse.e4.ui.workbench.modeling.ISelectionListener targetedPostListener = new org.eclipse.e4.ui.workbench.modeling.ISelectionListener() {
		@Override
		public void selectionChanged(MPart part, Object selection) {
			handlePostSelectionChanged(part, selection, true);
		}
	};
