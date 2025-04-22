
		final Shell winShell = (Shell) window.getWidget();
		winShell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (!winShell.isDisposed()) {
					winShell.layout(true, true);
				}
			}
		});
	}
