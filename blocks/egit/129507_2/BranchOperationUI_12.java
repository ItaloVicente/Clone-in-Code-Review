	private void handleMultipleRepositoryCheckoutError(
			Map<Repository, CheckoutResult> results) {

		PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
			Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				new MultiBranchOperationResultDialog(shell, results).open();
			}
		);
	}

