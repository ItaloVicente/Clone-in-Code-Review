				config.get(CommitConfig.KEY).getCommitTemplateContent(repo));
		assertNull("no commitEncoding has been set so it must be null"
				commitEncoding);
	}

	@Test
	public void testCommitTemplateConfigRelativePath()
			throws ConfigInvalidException

		File workTree = tmp.newFolder("dummy-worktree");
		File tempFile = tmp.newFile("testCommitTemplate-");
		String templateContent = "content of the template";
		JGitTestUtil.write(tempFile
		String expectedTemplatePath = "../" + tempFile.getName();

		Config config = parse(
				"[commit]\n\ttemplate = " + expectedTemplatePath + "\n");

		String templatePath = config.get(CommitConfig.KEY)
				.getCommitTemplatePath();
		String commitEncoding = config.get(CommitConfig.KEY)
				.getCommitEncoding();
		assertEquals(expectedTemplatePath
		assertEquals(templateContent
				.getCommitTemplateContent(
						new RepositoryBuilder().setWorkTree(workTree).build()));
