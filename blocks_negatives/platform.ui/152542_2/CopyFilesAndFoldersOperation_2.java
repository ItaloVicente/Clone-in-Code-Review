		messageShell.getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				ErrorDialog.openError(messageShell, getProblemsTitle(), null,
						status);
			}
		});
