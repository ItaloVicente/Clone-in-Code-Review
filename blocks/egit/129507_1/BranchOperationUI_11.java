	private void handleMultipleRepositoryCheckoutError(
			Map<Repository, CheckoutResult> results) {

		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				new MultiBranchOperationResultDialog(shell, results).open();
			}
		});
	}

