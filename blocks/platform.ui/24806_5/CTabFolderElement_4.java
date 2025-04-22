	@Override
	public void initialize() {
		super.initialize();
		((CTabFolder) getControl()).addSelectionListener(selectionListener);
	}

	@Override
	public void dispose() {
		CTabFolder ctf = (CTabFolder) getControl();
		if (ctf != null && !ctf.isDisposed()) {
			ctf.removeSelectionListener(selectionListener);
		}
		super.dispose();
	}

