	@Test
	public void testDiffAutoCrlfSmallFile() throws Exception {
		String content = "01234\r\n01234\r\n01234\r\n";
		String expectedDiff = "diff --git a/test.txt b/test.txt\n"
				+ "@@ -1
				+ " 01234\n";
		doAutoCrLfTest(content
	}

	@Test
	public void testDiffAutoCrlfMediumFile() throws Exception {
		String content = mediumCrLfString();
		String expectedDiff = "diff --git a/test.txt b/test.txt\n"
				+ "@@ -1
				+ " 01234567\n";
		doAutoCrLfTest(content
	}

	@Test
	public void testDiffAutoCrlfLargeFile() throws Exception {
		String content = largeCrLfString();
		String expectedDiff = "diff --git a/test.txt b/test.txt\n"
				+ "@@ -1
				+ " 012345678901234567890123456789012345678901234567\n"
				+ "+ABCD\n"
				+ " 012345678901234567890123456789012345678901234567\n"
				+ " 012345678901234567890123456789012345678901234567\n"
				+ " 012345678901234567890123456789012345678901234567\n";
		doAutoCrLfTest(content
	}

	private void doAutoCrLfTest(String content
			throws Exception {
		FileBasedConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOCRLF
		config.save();
		commitFile("test.txt"
		int i = content.indexOf('\n');
		content = content.substring(0
				+ content.substring(i + 1);
		writeTrashFile("test.txt"
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();
				DiffFormatter dfmt = new DiffFormatter(
						new BufferedOutputStream(os))) {
			dfmt.setRepository(db);
			dfmt.format(new DirCacheIterator(db.readDirCache())
					new FileTreeIterator(db));
			dfmt.flush();

			String actual = os.toString("UTF-8");

			assertEquals(expectedDiff
		}
	}

	private static String largeCrLfString() {
		String line = "012345678901234567890123456789012345678901234567\r\n";
		StringBuilder builder = new StringBuilder(
				2 * RawText.FIRST_FEW_BYTES);
		while (builder.length() < 2 * RawText.FIRST_FEW_BYTES) {
			builder.append(line);
		}
		return builder.toString();
	}

	private static String mediumCrLfString() {
		StringBuilder builder = new StringBuilder(
				RawText.FIRST_FEW_BYTES + line.length());
		while (builder.length() <= RawText.FIRST_FEW_BYTES) {
			builder.append(line);
		}
		return builder.toString();
	}

