	@AfterClass
	@BeforeClass
	public static void cleanup() {
		try {
			FileUtils.delete(new File(".niogit")
		} catch (IOException ex) {
		}
	}

	@Test
	public void testSimpleBuilderSample() throws IOException {
		final FileSystem fs = JGitFileSystemBuilder.newFileSystem("myrepo");

		Path foo = fs.getPath("/foo");
		Files.createDirectory(foo);


		Files.write(hello

		assertEquals("hello world"
	}
