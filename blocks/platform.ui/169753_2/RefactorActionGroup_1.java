	@Override
	public void dispose() {
		if (textActionHandler != null) {
			textActionHandler.dispose();
		}
		super.dispose();
	}
