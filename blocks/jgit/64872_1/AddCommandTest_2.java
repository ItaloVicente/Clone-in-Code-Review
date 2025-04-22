		try (Git git = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + slashify(script.getPath()) + " %f");
			writeTrashFile(".gitattributes"

			git.add().addFilepattern("; echo virus").call();
			assertEquals("[; echo virus
					indexState(CONTENT));
		}
