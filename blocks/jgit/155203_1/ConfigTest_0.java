	@Test
	public void test008_readCommitTemplateConfig() {
		final MockSystemReader mockSystemReader = new MockSystemReader();
		SystemReader.setInstance(mockSystemReader);
		final Config userGitConfig = mockSystemReader.openUserConfig(null
				FS.DETECTED);
		final Config localConfig = new Config(userGitConfig);
		mockSystemReader.clearProperties();

		String templatePath;


		assertFalse(localConfig.get(UserConfig.KEY).getCommitTemplatePath()
				.isPresent());

		String expectedTemplatePath = "git commit template path";
		mockSystemReader.setProperty(Constants.GIT_COMMIT_TEMPLATE_KEY
				expectedTemplatePath);
		localConfig.uncache(UserConfig.KEY);
		templatePath = localConfig.get(UserConfig.KEY).getCommitTemplatePath()
				.get();
		assertEquals(expectedTemplatePath

		mockSystemReader.clearProperties();
		userGitConfig.setString("commit"
				expectedTemplatePath + "global");
		templatePath = localConfig.get(UserConfig.KEY).getCommitTemplatePath()
				.get();
		assertEquals(expectedTemplatePath + "global"

		localConfig.setString("commit"
				expectedTemplatePath + "local");
		templatePath = localConfig.get(UserConfig.KEY).getCommitTemplatePath()
				.get();
		assertEquals(expectedTemplatePath + "local"
	}

