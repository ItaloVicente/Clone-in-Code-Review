		Shell shell = window.getShell();
		if (shell != null && !shell.isDisposed()) {
			windowTitle = shell.getText();
		}
		return windowTitle;
	}

	@Override
