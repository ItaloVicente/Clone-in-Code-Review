		if (title == null) {
			throw new IllegalArgumentException();
		}
		windowTitle = title;
		Shell shell = window.getShell();
		if (shell != null && !shell.isDisposed()) {
			shell.setText(TextProcessor.process(title, WorkbenchWindow.TEXT_DELIMITERS));
		}
	}

	@Override
