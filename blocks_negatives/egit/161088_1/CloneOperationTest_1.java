		Repository clonedRepo = FileRepositoryBuilder.create(new File(workdir2,
				Constants.DOT_GIT));
		assertEquals(
				"",
				"HEAD:refs/for/master",
				clonedRepo.getConfig()
				.getString(ConfigConstants.CONFIG_REMOTE_SECTION,
						"origin", "push"));
		assertEquals(
				"",
				clonedRepo.getConfig().getString(
						ConfigConstants.CONFIG_REMOTE_SECTION, "origin",
						"pushurl"));
