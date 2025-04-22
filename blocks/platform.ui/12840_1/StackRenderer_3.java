				if (isCommandAvailable(COMMAND_FILE_CLOSE_ALL)) {
					MenuItem menuItemAll = new MenuItem(menu, SWT.NONE);
					menuItemAll.setText(SWTRenderersMessages.menuCloseAll);
					menuItemAll.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							invokeCloseCommand(COMMAND_FILE_CLOSE_ALL, parent,
									menu);
						}
					});
				}
