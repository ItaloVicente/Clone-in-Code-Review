				MenuItem menuItemAll = new MenuItem(menu, SWT.NONE);
				menuItemAll.setText(SWTRenderersMessages.menuCloseAll);
				menuItemAll.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						MPart part = (MPart) menu.getData(STACK_SELECTED_PART);
						closeSiblingParts(part, false);
					}
				});
