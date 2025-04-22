	@Test
	public void testBadFanoutTable() {
		try {
			try (FileInputStream fis = new FileInputStream(
					getFileForBadFanoutTable())) {
				PackIndex.read(fis);
			}
			fail();
		} catch (IOException ex) {
			assertEquals(JGitText.get().indexFileIsTooLargeForJgit
					ex.getMessage());
		}
	}
