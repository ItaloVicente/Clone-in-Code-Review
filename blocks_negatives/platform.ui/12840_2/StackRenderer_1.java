				MenuItem menuItemOthers = new MenuItem(menu, SWT.NONE);
				menuItemOthers.setText(SWTRenderersMessages.menuCloseOthers);
				menuItemOthers.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						MPart part = (MPart) menu.getData(STACK_SELECTED_PART);
						closeSiblingParts(part, true);
					}
				});
