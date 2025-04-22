	@Test
	public void testCommitTemplateConfig()
			throws ConfigInvalidException

		Config config = new Config(null);
		assertNull(config.get(CommitConfig.KEY).getCommitTemplatePath());
		assertNull(config.get(CommitConfig.KEY).getCommitTemplateContent());

		String fileNamePrefix = "testCommitTemplate-";

		File tempFile = File.createTempFile(fileNamePrefix
		tempFile.deleteOnExit();
		String templateContent = "content of the template";
		writeToFile(tempFile

		String expectedTemplatePath = tempFile.getPath();
		config = parse("[commit]\n\ttemplate = " + expectedTemplatePath + "\n");
		String templatePath = config.get(CommitConfig.KEY)
				.getCommitTemplatePath();
		assertEquals(expectedTemplatePath
		assertEquals(templateContent
				config.get(CommitConfig.KEY).getCommitTemplateContent());

		config = parse("[i18n]\n\tcommitEncoding = utf-8\n"
				+ "[commit]\n\ttemplate = " + expectedTemplatePath + "\n");
		assertEquals(templateContent
				config.get(CommitConfig.KEY).getCommitTemplateContent());

		String homeDir = System.getProperty("user.home");
		File tempFileInHomeDirectory = File.createTempFile(
				fileNamePrefix
				new File(homeDir));
		tempFileInHomeDirectory.deleteOnExit();
		writeToFile(tempFileInHomeDirectory
		expectedTemplatePath = tempFileInHomeDirectory.getPath();
		expectedTemplatePath = expectedTemplatePath.replace(homeDir

		config = parse("[commit]\n\ttemplate = " + expectedTemplatePath + "\n");
		templatePath = config.get(CommitConfig.KEY).getCommitTemplatePath();
		assertEquals(expectedTemplatePath
		assertEquals(templateContent
				config.get(CommitConfig.KEY).getCommitTemplateContent());

	}

