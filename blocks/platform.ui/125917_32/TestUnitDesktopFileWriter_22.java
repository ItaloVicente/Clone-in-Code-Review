		assertEquals("/usr/bin/eclipse (copy)", writer.getExecutableLocation());
	}

	@Test
	public void returnsUnescapedMultipleSpacesExecutablePathWithoutParameter() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse\\ \\ (copy) %u", "MimeType=x-scheme-handler/adt;"));

		assertEquals("/usr/bin/eclipse  (copy)", writer.getExecutableLocation());
