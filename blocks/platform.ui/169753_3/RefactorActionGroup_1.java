	@Override
	public void dispose() {
		if (textActionHandler != null) {
			textActionHandler.dispose();
		}
		textActionHandler = null;
		super.dispose();
	}
