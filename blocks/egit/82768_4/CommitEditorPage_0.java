	@Override
	public void dispose() {
		if (globalActionHandler != null) {
			globalActionHandler.dispose();
		}
	}

