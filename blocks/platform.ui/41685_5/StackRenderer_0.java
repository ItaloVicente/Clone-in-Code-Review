				int leftFrom = getCloseableSideParts(part, true).size();
				if (leftFrom > 0) {
					MenuItem menuItemLeft = new MenuItem(menu, SWT.NONE);
					menuItemLeft.setText(SWTRenderersMessages.menuCloseLeft);
					menuItemLeft.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							MPart part = (MPart) menu.getData(STACK_SELECTED_PART);
							closeSideParts(part, true);
						}
					});
				}

				int rightFrom = getCloseableSideParts(part, false).size();
				if (rightFrom > 0) {
					MenuItem menuItemRight = new MenuItem(menu, SWT.NONE);
					menuItemRight.setText(SWTRenderersMessages.menuCloseRight);
					menuItemRight.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							MPart part = (MPart) menu.getData(STACK_SELECTED_PART);
							closeSideParts(part, false);
						}
					});
				}

				new MenuItem(menu, SWT.SEPARATOR);

