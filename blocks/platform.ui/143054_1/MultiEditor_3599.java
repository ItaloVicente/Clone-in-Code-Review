	public Composite createInnerPartControl(Composite parent, final IEditorPart e) {
		Composite content = new Composite(parent, SWT.NONE);
		content.setLayout(new FillLayout());
		e.createPartControl(content);
		parent.addListener(SWT.Activate, event -> {
			if (event.type == SWT.Activate) {
