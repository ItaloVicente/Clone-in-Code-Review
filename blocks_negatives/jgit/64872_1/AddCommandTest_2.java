		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter", "tstFilter", "clean",
				"sh " + slashify(script.getPath()) + " %f");
		writeTrashFile(".gitattributes", "* filter=tstFilter");

		git.add().addFilepattern("; echo virus").call();
		assertEquals("[; echo virus, mode:100644, content:fee\n]",
				indexState(CONTENT));
