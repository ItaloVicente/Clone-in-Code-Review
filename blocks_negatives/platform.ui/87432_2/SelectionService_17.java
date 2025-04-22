	private org.eclipse.e4.ui.workbench.modeling.ISelectionListener targetedListener = new org.eclipse.e4.ui.workbench.modeling.ISelectionListener() {
		@Override
		public void selectionChanged(MPart part, Object selection) {
			handleSelectionChanged(part, selection, true);
		}
	};
