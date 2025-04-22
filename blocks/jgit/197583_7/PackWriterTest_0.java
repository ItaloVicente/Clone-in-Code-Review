	@Test
	public void testWriteReverseIndexConfig() throws Exception {
		assertTrue(config.isWriteReverseIndex());
		config.setWriteReverseIndex(false);
		assertFalse(config.isWriteReverseIndex());
	}

	@Test
	public void testWriteReverseIndexOff() throws Exception {
		config.setWriteReverseIndex(false);
		writer = new PackWriter(config
		ByteArrayOutputStream reverseIndexOutput = new ByteArrayOutputStream();

		writer.writeReverseIndex(reverseIndexOutput);

		assertEquals(0
	}

	@Test
	public void testWriteReverseIndexOn() throws Exception {
		writeVerifyPack4(false);
		ByteArrayOutputStream reverseIndexOutput = new ByteArrayOutputStream();

		writer.writeReverseIndex(reverseIndexOutput);

		assertTrue(reverseIndexOutput.size() > OBJECT_ID_LENGTH * 3);
	}

