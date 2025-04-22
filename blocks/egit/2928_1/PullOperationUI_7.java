		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				showResults(shell);
			}
		});

	}

	private void showResults(final Shell shell) {
		if (this.results.isEmpty())
			return;
		else if (this.results.size() == 1) {
			Entry<Repository, Object> entry = this.results.entrySet()
					.iterator().next();
			if (entry.getValue() instanceof PullResult)
				new PullResultDialog(shell, entry.getKey(), (PullResult) entry
						.getValue()).open();
			else {
				IStatus status = (IStatus) entry.getValue();
				if (status == NOT_TRIED_STATUS) {
