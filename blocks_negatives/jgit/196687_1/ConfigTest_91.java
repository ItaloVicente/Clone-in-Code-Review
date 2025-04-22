	@Test(expected = FileNotFoundException.class)
	public void testCommitTemplateWithInvalidPath()
			throws ConfigInvalidException, IOException {
		Config config = new Config(null);
		File workTree = tmp.newFolder("dummy-worktree");
		File tempFile = tmp.newFile("testCommitTemplate-");
		Repository repo = FileRepositoryBuilder.create(workTree);
		String templateContent = "content of the template";
		JGitTestUtil.write(tempFile, templateContent);
		String expectedTemplatePath = "~/nonExistingTemplate";
		config = parse("[commit]\n\ttemplate = " + expectedTemplatePath + "\n");
		String templatePath = config.get(CommitConfig.KEY)
				.getCommitTemplatePath();
		assertEquals(expectedTemplatePath, templatePath);
		config.get(CommitConfig.KEY).getCommitTemplateContent(repo);
