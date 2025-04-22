	@Test
	public void testCommitTemplateConfig()
			throws ConfigInvalidException

		Config config = new Config(null);
		assertNull(config.get(CommitConfig.KEY).getCommitTemplatePath());
		assertNull(config.get(CommitConfig.KEY).getCommitTemplateContent());

		File tempFile = File.createTempFile("testCommitTemplate-"
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
	}

