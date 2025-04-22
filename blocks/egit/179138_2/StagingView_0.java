	private void saveSashFormOrientationOnDisposal(final SashForm sashForm,
			final String verticalOrientationSettingsKey) {
		sashForm.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				getDialogSettings().put(verticalOrientationSettingsKey,
						sashForm.getOrientation() == SWT.VERTICAL);
			}
		});
	}

