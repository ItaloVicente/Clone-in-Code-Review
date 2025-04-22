		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = PlatformUI.getWorkbench()
						.getModalDialogShellProvider().getShell();
				new FetchResultDialog(shell, repository, result, sourceString)
						.open();
			}
		});
