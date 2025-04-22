	@Test
	public void testCommitTemplateConfig() throws ConfigInvalidException {

		Config config = new Config(null);
		assertFalse(config.get(CommitConfig.KEY).getCommitTemplatePath()
				.isPresent());

		String expectedTemplatePath = "git commit template path";
		config = parse("[commit]\n\ttemplate = " + expectedTemplatePath + "\n");
		String templatePath = config.get(CommitConfig.KEY)
				.getCommitTemplatePath()
				.get();
		assertEquals(expectedTemplatePath
	}

