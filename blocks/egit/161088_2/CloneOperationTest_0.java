		try (Repository clonedRepo = FileRepositoryBuilder.create(new File(workdir2,
				Constants.DOT_GIT))) {
			assertEquals(
					"",
					uri.toString(),
					clonedRepo.getConfig().getString(
							ConfigConstants.CONFIG_REMOTE_SECTION, "origin", "url"));
			assertEquals(
					"",
					"+refs/heads/*:refs/remotes/origin/*",
					clonedRepo.getConfig().getString(
							ConfigConstants.CONFIG_REMOTE_SECTION, "origin",
							"fetch"));
		}
