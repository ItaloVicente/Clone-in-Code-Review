
		if(result.getStatus() == Status.CONFLICTS) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					Shell shell = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell();
					new CheckoutConflictDialog(shell, repository, result.getConflicts()).open();
				}
			});

			return;
		}

