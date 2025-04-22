			if (repositories.length == 1) {
				if (itemCount > 0)
					createSeparator(menu);
				MenuItem others = new MenuItem(menu, SWT.PUSH);
				others.setText(UIText.SwitchToMenu_OtherMenuLabel);
				others.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						CheckoutDialog dialog = new CheckoutDialog(
								e.display.getActiveShell(), repositories[0]);
						if (dialog.open() == Window.OK) {
							BranchOperationUI.checkout(repositories[0],
									dialog.getRefName()).start();
						}
