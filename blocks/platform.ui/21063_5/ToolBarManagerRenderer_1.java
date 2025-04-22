	private void createToolbarMenu(final MToolBar toolbarModel,
			Control renderedCtrl) {
		toolbarMenu = new Menu(renderedCtrl);
		MenuItem closeItem = new MenuItem(toolbarMenu, SWT.NONE);
		closeItem.setText(Messages.ToolBarManagerRenderer_MenuCloseText);
		closeItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				toolbarModel.getTags().add(IPresentationEngine.HIDDEN_BY_USER);
			}
		});

		MenuItem restoreItem = new MenuItem(toolbarMenu, SWT.NONE);
		restoreItem.setText(Messages.ToolBarManagerRenderer_MenuRestoreText);
		closeItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
			}
		});
		renderedCtrl.setMenu(toolbarMenu);
	}

