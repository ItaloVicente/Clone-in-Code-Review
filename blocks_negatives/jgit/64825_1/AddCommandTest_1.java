		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter", "tstFilter", "clean",
				"sh " + slashify(script.getPath()));
		config.setString("filter", "tstFilter2", "clean",
				"sh " + slashify(script2.getPath()));
		config.save();
