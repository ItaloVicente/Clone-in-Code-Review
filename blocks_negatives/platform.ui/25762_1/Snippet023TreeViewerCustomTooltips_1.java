	public Snippet023TreeViewerCustomTooltips(Shell shell) {
		final TreeViewer v = new TreeViewer(shell);
		v.setLabelProvider(new LabelProvider());
		v.setContentProvider(new MyContentProvider());
		v.setInput(createModel());
		v.getTree().setToolTipText("");

		final Listener labelListener = new Listener () {
			public void handleEvent (Event event) {
				Label label = (Label)event.widget;
				Shell shell = label.getShell ();
