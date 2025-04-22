	public void setErrorMessage(final String message) {
		getHistoryPageSite().getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				StackLayout layout = (StackLayout) getControl().getParent()
						.getLayout();
				if (message != null) {
					errorText.setText(message);
					layout.topControl = errorText;
				} else {
					errorText.setText(""); //$NON-NLS-1$
					layout.topControl = getControl();
				}
				getControl().getParent().layout();
			}
		});
