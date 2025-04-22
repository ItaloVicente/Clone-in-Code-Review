	public void returnsRegisteredSchemeTwoRequested() {
		PlistFileWriter writer = getWriterWithSchemes("adt");

		List<String> schemes = writer.getRegisteredSchemes(Arrays.asList("adt", "other"));
		assertEquals(1, schemes.size());
		assertTrue(schemes.contains("adt"));
	}

	@Test
	public void returnsNoRegisteredSchemeTwoRequested() {
		PlistFileWriter writer = getWriterWithSchemes("yetAnother");

		List<String> schemes = writer.getRegisteredSchemes(Arrays.asList("adt", "other"));
		assertEquals(0, schemes.size());
	}

	@Test
	public void returnsNoRegisteredSchemeTwoRequestedNoneRegistered() {
