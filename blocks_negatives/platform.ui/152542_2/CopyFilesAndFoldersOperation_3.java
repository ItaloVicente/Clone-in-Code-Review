		messageShell.getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				MessageDialog.openError(messageShell, getProblemsTitle(),
						message);
			}
		});
