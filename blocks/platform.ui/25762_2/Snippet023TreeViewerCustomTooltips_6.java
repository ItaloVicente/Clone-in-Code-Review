	private Listener createLabelListenerFor(final TreeViewer v) {
		return new Listener() {

			private Label label;
			private Shell shell;

			@Override
			public void handleEvent(Event event) {
				this.label = (Label) event.widget;
				this.shell = label.getShell();
				switch (event.type) {
				case SWT.MouseDown:
					onMouseDown();
					break;
				case SWT.MouseExit:
					shell.dispose();
					break;
				}
			}

			private void onMouseDown() {
				Event e = new Event();
				e.item = (TreeItem) label.getData("_TABLEITEM");
				v.getTree().setSelection(new TreeItem[] { (TreeItem) e.item });
				v.getTree().notifyListeners(SWT.Selection, e);
				shell.dispose();
				v.getTree().setFocus();
			}
		};
	}

	private MyModel createModel() {
