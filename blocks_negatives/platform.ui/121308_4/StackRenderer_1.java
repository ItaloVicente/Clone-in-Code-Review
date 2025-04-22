				MenuItem menuItemOthers = new MenuItem(menu, SWT.NONE);
				menuItemOthers.setText(SWTRenderersMessages.menuCloseOthers);
				menuItemOthers.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						MPart part = (MPart) menu.getData(STACK_SELECTED_PART);
						closeSiblingParts(part, true);
					}
				});

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
