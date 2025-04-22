		final String fMessage = message;
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openWarning(wp.getSite().getShell(), title, fMessage);
			}
		});

