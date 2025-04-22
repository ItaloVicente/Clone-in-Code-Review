	@Override
	protected void doTearDown() throws Exception {
		super.doTearDown();
		if (dialog != null) {
			dialog.dispose();
		}
	}

