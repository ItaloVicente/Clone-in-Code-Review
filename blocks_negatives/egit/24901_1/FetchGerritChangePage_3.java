					shell.getDisplay().asyncExec(new Runnable() {
						public void run() {
							new CheckoutConflictDialog(shell, repository,
									result.getConflictList()).open();
						}
					});
				}
