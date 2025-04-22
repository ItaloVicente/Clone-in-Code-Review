		try (Git git = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + script.getPath());
			config.save();
			writeTrashFile(".gitattributes"
