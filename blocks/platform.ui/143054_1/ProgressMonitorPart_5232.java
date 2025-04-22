		if (!fLabel.isDisposed()) {
			fLabel.setText("");//$NON-NLS-1$
		}
		fSubTaskName = ""; //$NON-NLS-1$
		if (!fProgressIndicator.isDisposed()) {
			fProgressIndicator.sendRemainingWork();
			fProgressIndicator.done();
		}
		if (fToolBar != null && !fToolBar.isDisposed())
			fToolBar.setVisible(false);
	}

	protected static String escapeMetaCharacters(String in) {
		if (in == null || in.indexOf('&') < 0) {
