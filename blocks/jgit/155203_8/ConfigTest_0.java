	@Test
	public void testCommitTemplateConfig()
			throws ConfigInvalidException

		Config config = new Config(null);
		assertFalse(config.get(CommitConfig.KEY).getCommitTemplatePath()
				.isPresent());
		assertFalse(config.get(CommitConfig.KEY).getCommitTemplateContent()
				.isPresent());

		File tempFile = File.createTempFile("testCommitTemplate-"
		tempFile.deleteOnExit();
		String templateContent = "content of the template";
		writeToFile(tempFile

		String expectedTemplatePath = tempFile.getPath();
		config = parse("[commit]\n\ttemplate = " + expectedTemplatePath + "\n");
		String templatePath = config.get(CommitConfig.KEY)
				.getCommitTemplatePath()
				.get();
		assertEquals(expectedTemplatePath
		assertEquals(templateContent
				config.get(CommitConfig.KEY).getCommitTemplateContent().get());
	}

