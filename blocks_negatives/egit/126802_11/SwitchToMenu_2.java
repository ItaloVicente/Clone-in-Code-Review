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
					}
