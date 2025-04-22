		return new ApplicationWindowLayout();
	}

	protected boolean showTopSeperator() {
		return !Util.isMac();
	}

	protected void createStatusLine(Shell shell) {
		if (statusLineManager != null) {
			statusLineManager.createControl(shell, SWT.NONE);
		}
	}

	protected MenuManager createMenuManager() {
		return new MenuManager();
	}

	protected StatusLineManager createStatusLineManager() {
		return new StatusLineManager();
	}

	protected ToolBarManager createToolBarManager(int style) {
		return new ToolBarManager(style);
	}

