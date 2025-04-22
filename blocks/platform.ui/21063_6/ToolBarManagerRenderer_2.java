	private void createToolbarMenu(final MToolBar toolbarModel,
			Control renderedCtrl) {
		toolbarMenu = new Menu(renderedCtrl);
		MenuItem hideItem = new MenuItem(toolbarMenu, SWT.NONE);
		hideItem.setText(Messages.ToolBarManagerRenderer_MenuCloseText);
		hideItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				toolbarModel.getTags().add(IPresentationEngine.HIDDEN_BY_USER);
			}
		});

		new MenuItem(toolbarMenu, SWT.SEPARATOR);

		MenuItem restoreHiddenItems = new MenuItem(toolbarMenu, SWT.NONE);
		restoreHiddenItems.setText(Messages.ToolBarManagerRenderer_MenuRestoreText);
		restoreHiddenItems.addListener(SWT.Selection, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				removeHiddenByUserTags();
			}
		});
		renderedCtrl.setMenu(toolbarMenu);
	}

