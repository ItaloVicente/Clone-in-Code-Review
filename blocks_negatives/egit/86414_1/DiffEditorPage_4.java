	@Override
	protected void createFormContent(IManagedForm managedForm) {
		Composite body = managedForm.getForm().getBody();
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(body);

		viewer = new DiffViewer(body, new CompositeRuler(), SWT.V_SCROLL
				| SWT.H_SCROLL, true);
		viewer.setEditable(false);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(viewer.getControl());

		MenuManager manager = new MenuManager();
		addEditorActions(manager);
		Menu menu = manager.createContextMenu(viewer.getTextWidget());
		IEditorSite site = getEditorSite();
		site.setSelectionProvider(viewer);
		site.registerContextMenu(
				AbstractTextEditor.COMMON_EDITOR_CONTEXT_MENU_ID, manager,
				viewer, true);
		site.registerContextMenu(
				AbstractTextEditor.DEFAULT_EDITOR_CONTEXT_MENU_ID, manager,
				viewer, true);
		viewer.getTextWidget().setMenu(menu);

		formatDiff();
