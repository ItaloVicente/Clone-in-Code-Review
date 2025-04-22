		FileRepository repo1initial = new FileRepository(new File(repo1Parent,
				Constants.DOT_GIT));
		repo1initial.create();
		final FileBasedConfig cfg = repo1initial.getConfig();
		cfg.setString("core", null, "worktree", workdir.getAbsolutePath());
		cfg.save();
		repo1initial.close();
