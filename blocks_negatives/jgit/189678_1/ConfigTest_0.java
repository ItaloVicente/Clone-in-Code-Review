	@Test
	public void testCommitTemplatePathInHomeDirecory()
			throws ConfigInvalidException, IOException {
		Config config = new Config(null);
		File tempFile = tmp.newFile("testCommitTemplate-");
		File workTree = tmp.newFolder("dummy-worktree");
		Repository repo = FileRepositoryBuilder.create(workTree);
		String templateContent = "content of the template";
		JGitTestUtil.write(tempFile, templateContent);
		String homeDir = System.getProperty("user.home");
		File tempFileInHomeDirectory = File.createTempFile("fileInHomeFolder",
				".tmp", new File(homeDir));
		tempFileInHomeDirectory.deleteOnExit();
		JGitTestUtil.write(tempFileInHomeDirectory, templateContent);
		String expectedTemplatePath = tempFileInHomeDirectory.getPath()
				.replace(homeDir, "~");
		config = parse("[commit]\n\ttemplate = " + expectedTemplatePath + "\n");
		String templatePath = config.get(CommitConfig.KEY)
				.getCommitTemplatePath();
		assertEquals(expectedTemplatePath, templatePath);
		assertEquals(templateContent,
				config.get(CommitConfig.KEY).getCommitTemplateContent(repo));
	}

