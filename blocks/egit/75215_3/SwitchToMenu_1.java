					CheckoutDialog dialog = new CheckoutDialog(
							e.display.getActiveShell(), repository);
					if (dialog.open() == Window.OK) {
						BranchOperationUI
								.checkout(repository, dialog.getRefName())
								.start();
					}

