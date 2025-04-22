	@Test
	public void testIncludeDontInlineIncludedLinesOnSave()
			throws IOException
		final File includedFile = createFile(CONTENT3.getBytes()

		final File file = createFile(new byte[0]
		FileBasedConfig config = new FileBasedConfig(file
		config.setString("include"
				("../" + includedFile.getParentFile().getName() + "/"
						+ includedFile.getName()));

		assertEquals(null
		assertEquals(null
		config.save();

		assertEquals(null
		assertEquals(null

		final String expectedText = config.toText();
		assertEquals(2
				new StringTokenizer(expectedText

		config = new FileBasedConfig(file
		config.load();

		String actualText = config.toText();
		assertEquals(expectedText
		assertEquals(ALICE
		assertEquals(ALICE_EMAIL

		config.save();

		actualText = config.toText();
		assertEquals(expectedText
		assertEquals(ALICE
		assertEquals(ALICE_EMAIL
	}

