		};
		stageJob.setUser(true);
		stageJob.schedule();
	}

	private void syncOpenError(final Shell shell, final String title, final String message) {
		shell.getDisplay().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(shell, title, message);
			}
		});
