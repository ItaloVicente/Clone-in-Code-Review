		};
		if (immediately) {
			update.run();
		}
		else {
			Shell shell = window.getShell();
			if (shell != null && !shell.isDisposed()) {
				shell.getDisplay().asyncExec(update);
			}
		}
	}
