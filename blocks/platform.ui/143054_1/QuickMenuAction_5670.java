	public void dispose() {
		if (creator != null) {
			creator.dispose();
			creator = null;
		}
	}
