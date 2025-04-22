				menuItemAll.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						MPart part = (MPart) menu.getData(STACK_SELECTED_PART);
						closeSiblingParts(part, false);
					}
				});
