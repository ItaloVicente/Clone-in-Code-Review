		Shell shell = fShell;
		if (shell != null && !shell.isDisposed()) {
			Display display = shell.getDisplay();
			if (display != null) {
				while (display.readAndDispatch()) {
				}
			}
		}
