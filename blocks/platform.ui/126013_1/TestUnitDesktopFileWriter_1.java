	@Test
	public void returnsRegisteredSchemeOnerequested() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse %u", "MimeType=x-scheme-handler/adt;"));

		List<String> schemes = writer.getRegisteredSchemes(Arrays.asList("adt"));
		assertEquals(1, schemes.size());
		assertTrue(schemes.contains("adt"));
	}

	@Test
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
		DesktopFileWriter writer = getWriterFor(fileContentWith("Exec=/usr/bin/eclipse %u", ""));

		List<String> schemes = writer.getRegisteredSchemes(Arrays.asList("adt", "other"));
		assertEquals(0, schemes.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getRegisteredFailsOnIllegalScheme() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse %u", "MimeType=x-scheme-handler/adt;"));

		writer.getRegisteredSchemes(Arrays.asList("&/%"));
	}

