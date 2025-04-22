					if (itemCount > MAX_NUM_MENU_ENTRIES)
						break;
					final String fullName = entry.getValue().getName();
					final String shortName = entry.getKey();
					createMenuItem(menu, repository, currentBranch, fullName, shortName);
				}
			}
			if (itemCount > 0)
				createSeparator(menu);
			MenuItem others = new MenuItem(menu, SWT.PUSH);
			others.setText(UIText.SwitchToMenu_OtherMenuLabel);
			others.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					CheckoutDialog dialog = new CheckoutDialog(
							e.display.getActiveShell(), repository);
					if (dialog.open() == Window.OK) {
						BranchOperationUI
								.checkout(repository, dialog.getRefName())
								.start();
