
	@Test
	public void testRelativize_doc() {
		String base = toOSPathString("c:\\Users\\jdoe\\eclipse\\git\\project");
		String other = toOSPathString("c:\\Users\\jdoe\\eclipse\\git\\another_project\\pom.xml");
		String expected = toOSPathString("..\\another_project\\pom.xml");

		String actual = FileUtils.relativize(base
		assertEquals(expected
	}

	@Test
	public void testRelativize_mixedCase() {
		SystemReader systemReader = SystemReader.getInstance();
		String base = toOSPathString("C:\\git\\jgit");
		String other = toOSPathString("C:\\Git\\test\\d\\f.txt");
		String expectedCaseInsensitive = toOSPathString("..\\test\\d\\f.txt");
		String expectedCaseSensitive = toOSPathString("..\\..\\Git\\test\\d\\f.txt");

		if (systemReader.isWindows()) {
			String actual = FileUtils.relativize(base
			assertEquals(expectedCaseInsensitive
		} else if (systemReader.isMacOS()) {
			String actual = FileUtils.relativize(base
			assertEquals(expectedCaseInsensitive
		} else {
			String actual = FileUtils.relativize(base
			assertEquals(expectedCaseSensitive
		}
	}

	@Test
	public void testRelativize_scheme() {
		String base = toOSPathString("file:/home/eclipse/runtime-New_configuration/project_1/file.java");
		String other = toOSPathString("file:/home/eclipse/runtime-New_configuration/project");
		String expected = toOSPathString("../../project");

		String actual = FileUtils.relativize(base
		assertEquals(expected
	}

	@Test
	public void testRelativize_equalPaths() {
		String base = toOSPathString("file:/home/eclipse/runtime-New_configuration/project_1");
		String other = toOSPathString("file:/home/eclipse/runtime-New_configuration/project_1");
		String expected = "";

		String actual = FileUtils.relativize(base
		assertEquals(expected
	}

	@Test
	public void testRelativize_whitespaces() {
		String base = toOSPathString("/home/eclipse 3.4/runtime New_configuration/project_1");
		String other = toOSPathString("/home/eclipse 3.4/runtime New_configuration/project_1/file");
		String expected = "file";

		String actual = FileUtils.relativize(base
		assertEquals(expected
	}

	private String toOSPathString(String path) {
		return path.replaceAll("/|\\\\"
				Matcher.quoteReplacement(File.separator));
	}
