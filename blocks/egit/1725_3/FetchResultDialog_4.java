
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell
				.setText(NLS.bind(UIText.FetchResultDialog_title, sourceString));
	}
