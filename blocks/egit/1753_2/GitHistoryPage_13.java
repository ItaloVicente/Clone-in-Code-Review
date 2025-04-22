	public void setErrorMessage(final String message) {
		graph.getControl().getDisplay().syncExec(new Runnable() {
			public void run() {
				if (!graph.getControl().isDisposed()) {
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
			}
		});
