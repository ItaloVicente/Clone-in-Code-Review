		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter", "tstFilter", "something",
				"sh " + script.getPath());
		config.save();
		writeTrashFile(".gitattributes", "*.txt filter=tstFilter");
