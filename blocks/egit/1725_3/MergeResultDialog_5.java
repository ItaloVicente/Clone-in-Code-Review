	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(UIText.MergeResultDialog_mergeResult);
		newShell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (objectReader != null)
					objectReader.release();
			}
		});
	}
