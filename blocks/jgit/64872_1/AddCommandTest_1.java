		try (Git git = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + slashify(script.getPath()));
			config.setString("filter"
					"sh " + slashify(script2.getPath()));
			config.save();
