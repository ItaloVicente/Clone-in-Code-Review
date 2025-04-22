	@Test
	public void testListAllTagsInOrionServer() throws Exception {
		File directory = createTempDirectory("testListAllTagsInOrionServer");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		Git git = command.call();
		List<RevTag> list = git.tagList().call();
		assertTrue(list.size() > 0);
	}
