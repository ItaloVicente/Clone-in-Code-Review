		winShell.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!winShell.isDisposed()) {
					winShell.layout(true, true);
				}
