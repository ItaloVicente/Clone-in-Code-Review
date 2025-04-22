	@Override
	protected void performApply() {
		try {
			editor.save();
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
		super.performApply();
	}

