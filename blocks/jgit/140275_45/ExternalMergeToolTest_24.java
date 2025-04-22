
	private void assertEchoCommandHasCorrectOutput() throws IOException {
		List<String> actualLines = Files.readAllLines(commandResult.toPath());
		String actualContent = String.join(System.lineSeparator()
		actualLines = Arrays.asList(actualContent.split(" "));
		List<String> expectedLines = Arrays.asList(localFile.getAbsolutePath()
				remoteFile.getAbsolutePath()
				baseFile.getAbsolutePath());
		assertEquals("Dummy test tool called with unexpected arguments"
				expectedLines
	}
