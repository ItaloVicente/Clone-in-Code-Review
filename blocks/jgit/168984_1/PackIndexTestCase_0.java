	@Test
	public void testBadFanoutTable() {
		IOException ex = assertThrows(IOException.class
			try (FileInputStream fis = new FileInputStream(
					getFileForBadFanoutTable())) {
				PackIndex.read(fis);
			}
		});
		assertEquals(JGitText.get().indexFileIsTooLargeForJgit
				ex.getMessage());
	}
