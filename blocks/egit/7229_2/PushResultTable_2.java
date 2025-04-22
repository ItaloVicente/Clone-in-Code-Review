		new OpenAndLinkWithEditorHelper(treeViewer) {
			@Override
			protected void linkToEditor(ISelection selection) {
			}
			@Override
			protected void open(ISelection selection, boolean activate) {
				handleOpen(selection, OpenStrategy.activateOnOpen());
			}
			@Override
			protected void activate(ISelection selection) {
				handleOpen(selection, true);
			}
			private void handleOpen(ISelection selection, boolean activateOnOpen) {
