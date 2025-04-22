	public void returnsRegisteredSchemeTwoRequested() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse %u", "MimeType=x-scheme-handler/adt;"));

		List<String> schemes = writer.getRegisteredSchemes(Arrays.asList("adt", "other"));
		assertEquals(1, schemes.size());
		assertTrue(schemes.contains("adt"));
	}

	@Test
	public void returnsNoRegisteredSchemeTwoRequested() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse %u", "MimeType=x-scheme-handler/yetAnother;"));

		List<String> schemes = writer.getRegisteredSchemes(Arrays.asList("adt", "other"));
		assertEquals(0, schemes.size());
	}

	@Test
	public void returnsNoRegisteredSchemeTwoRequestedNoneRegistered() {
