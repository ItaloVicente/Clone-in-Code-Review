	@Override
	public void beginTask(String name, int totalWork) {
		fTaskName = name;
		fSubTaskName = ""; //$NON-NLS-1$
		updateLabel();
		if (totalWork == IProgressMonitor.UNKNOWN || totalWork == 0) {
			fProgressIndicator.beginAnimatedTask();
		} else {
			fProgressIndicator.beginTask(totalWork);
		}
		if (fToolBar != null && !fToolBar.isDisposed()) {
			fToolBar.setVisible(true);
			fToolBar.setFocus();
		}
	}

	@Override
	public void done() {
		fLabel.setText("");//$NON-NLS-1$
		fSubTaskName = ""; //$NON-NLS-1$
		fProgressIndicator.sendRemainingWork();
		fProgressIndicator.done();
		if (fToolBar != null && !fToolBar.isDisposed())
			fToolBar.setVisible(false);
	}

	protected static String escapeMetaCharacters(String in) {
		if (in == null || in.indexOf('&') < 0) {
