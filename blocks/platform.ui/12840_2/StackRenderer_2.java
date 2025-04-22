				if (isCommandAvailable(COMMAND_FILE_CLOSE_OTHERS)) {
					MenuItem menuItemOthers = new MenuItem(menu, SWT.NONE);
					menuItemOthers
							.setText(SWTRenderersMessages.menuCloseOthers);
					menuItemOthers.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							invokeCloseCommand(COMMAND_FILE_CLOSE_OTHERS,
									parent, menu);
						}
					});
				}
