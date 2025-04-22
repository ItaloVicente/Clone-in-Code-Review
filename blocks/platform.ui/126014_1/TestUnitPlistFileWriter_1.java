	@Test
	public void returnsRegisteredSchemeOnerequested() {
		PlistFileWriter writer = getWriterWithSchemes("adt");

		List<String> schemes = writer.getRegisteredSchemes(Arrays.asList("adt"));
		assertEquals(1, schemes.size());
		assertTrue(schemes.contains("adt"));
	}

	@Test
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
		PlistFileWriter writer = getWriter();

		List<String> schemes = writer.getRegisteredSchemes(Arrays.asList("adt", "other"));
		assertEquals(0, schemes.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getRegisteredFailsOnIllegalScheme() {
		PlistFileWriter writer = getWriterWithSchemes("adt");

		writer.getRegisteredSchemes(Arrays.asList("&/%"));
	}

