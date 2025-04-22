	private void registerShellListener() {
		isActive.set(true);
		final Display display = PlatformUI.getWorkbench().getDisplay();
		if (display.isDisposed())
			return;
		display.asyncExec(new Runnable() {
			public void run() {
				Shell shell = display.getActiveShell();
				if (shell != null) {
					shell.addShellListener(new ShellAdapter() {

						public void shellActivated(ShellEvent e) {
							isActive.set(true);
						}

						public void shellClosed(ShellEvent e) {
							isActive.set(false);
						}

						public void shellDeactivated(ShellEvent e) {
							isActive.set(false);
						}
					});
				} else
					handleIssue(IStatus.WARNING,
							UIText.Activator_CantRegisterShellListener, null,
							false);
			}
		});
	}

