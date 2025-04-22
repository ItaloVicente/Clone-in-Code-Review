		assertEquals("commitEncoding has been set to utf-8 it must be utf-8",
				"utf-8", commitEncoding);
	}

	@Test(expected = ConfigInvalidException.class)
	public void testCommitTemplateWithInvalidEncoding()
			throws ConfigInvalidException, IOException {
		Config config = new Config(null);
		File workTree = tmp.newFolder("dummy-worktree");
		File tempFile = tmp.newFile("testCommitTemplate-");
		Repository repo = FileRepositoryBuilder.create(workTree);
		String templateContent = "content of the template";
		JGitTestUtil.write(tempFile, templateContent);
		config = parse("[i18n]\n\tcommitEncoding = invalidEcoding\n"
				+ "[commit]\n\ttemplate = "
				+ Config.escapeValue(tempFile.getPath()) + "\n");
		config.get(CommitConfig.KEY).getCommitTemplateContent(repo);
