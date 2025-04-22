	@After
	public void tearDown() {
		if (result != null) {
			result.dispose();
		}
		display.dispose();
	}
