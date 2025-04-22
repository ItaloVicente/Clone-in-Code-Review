		try (FileRepository repo1initial = new FileRepository(
				new File(repo1Parent
			repo1initial.create();
			final FileBasedConfig cfg = repo1initial.getConfig();
			cfg.setString("core"
			cfg.save();
		}
