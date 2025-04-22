
	@Test(expected = IllegalArgumentException.class)
	public void nullForNameShouldThrowIllegalArgumentException() {
		new PersonIdent(null
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullForEmailShouldThrowIllegalArgumentException() {
		new PersonIdent("A U Thor"
	}
