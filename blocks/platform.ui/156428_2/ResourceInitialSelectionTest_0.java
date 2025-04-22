	protected void spinEventLoop() {

		Display disp = dialog.getShell().getDisplay();
		while (disp.readAndDispatch()) {
		}
	}

