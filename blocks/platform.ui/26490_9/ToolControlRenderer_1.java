		if (hideable) {
			MenuItem hideItem = new MenuItem(toolControlMenu, SWT.NONE);
			hideItem.setText(Messages.ToolBarManagerRenderer_MenuCloseText);
			hideItem.addListener(SWT.Selection, new Listener() {
				public void handleEvent(org.eclipse.swt.widgets.Event event) {
					toolControl.getTags().add(
							IPresentationEngine.HIDDEN_EXPLICITLY);
				}
			});

			new MenuItem(toolControlMenu, SWT.SEPARATOR);
		}
