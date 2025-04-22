	private void createMenuToCloseToolbar(final MToolBar toolbarModel,
			Control renderedCtrl) {
		toolbarMenu = new Menu(renderedCtrl);
		MenuItem closeItem = new MenuItem(toolbarMenu, SWT.NONE);
		closeItem.setText(Messages.ToolBarManagerRenderer_MenuCloseText);
		closeItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				toolbarModel.setToBeRendered(false);
			}
		});
		renderedCtrl.setMenu(toolbarMenu);
	}

