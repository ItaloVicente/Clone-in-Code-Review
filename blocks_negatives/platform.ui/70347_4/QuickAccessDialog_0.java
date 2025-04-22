		getShell().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				final Shell shell = getShell();
				if (shell != null && !shell.isDisposed()) {
					Point size = shell.getSize();
					shell.setSize(size.x, size.y + 1);
				}
			}
		});
