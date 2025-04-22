
				int hidden = getCloseableParts(part, true).size();
				int visible = getCloseableParts(part, false).size();
				if (hidden > 0) {
					new MenuItem(menu, SWT.SEPARATOR);
				}
				if (hidden > 0) {
					MenuItem menuItemHidden = new MenuItem(menu, SWT.NONE);
					menuItemHidden.setText(SWTRenderersMessages.menuCloseHidden);
					menuItemHidden.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							MPart part = (MPart) menu.getData(STACK_SELECTED_PART);
							closeParts(part, true);
						}
					});
				}
				if (visible > 0 && hidden > 0) {
					MenuItem menuItemVisible = new MenuItem(menu, SWT.NONE);
					menuItemVisible.setText(SWTRenderersMessages.menuCloseVisible);
					menuItemVisible.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							MPart part = (MPart) menu.getData(STACK_SELECTED_PART);
							closeParts(part, false);
						}
					});
				}

