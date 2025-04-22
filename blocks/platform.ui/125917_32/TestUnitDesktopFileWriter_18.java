	public void returnsFalseWhenNoSchemeIsRegistered() {
		DesktopFileWriter writer = getWriterFor(fileContentWith("Exec=/usr/bin/eclipse %u", ""));

		assertFalse(writer.isRegistered("adt"));
		assertFalse(writer.isRegistered("other"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void isRegisteredFailsOnIllegalScheme() {
