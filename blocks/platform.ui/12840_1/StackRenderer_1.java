			if (isCommandAvailable(COMMAND_FILE_CLOSE)) {
				MenuItem menuItemClose = new MenuItem(menu, SWT.NONE);
				menuItemClose.setText(SWTRenderersMessages.menuClose);
				menuItemClose.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						invokeCloseCommand(COMMAND_FILE_CLOSE, parent, menu);
					}
				});
			}
