	@Test
	void testCommitTemplateWithInvalidPath() {
		assertThrows(FileNotFoundException.class
			Config config = new Config(null);
			File workTree = newFolder(tmp
			File tempFile = File.createTempFile("testCommitTemplate-"
			Repository repo = FileRepositoryBuilder.create(workTree);
			String templateContent = "content of the template";
			JGitTestUtil.write(tempFile
			String expectedTemplatePath = "~/nonExistingTemplate";
			config = parse("[commit]\n\ttemplate = " + expectedTemplatePath + "\n");
			String templatePath = config.get(CommitConfig.KEY)
					.getCommitTemplatePath();
			assertEquals(expectedTemplatePath
			config.get(CommitConfig.KEY).getCommitTemplateContent(repo);
		});
