		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		generator.next();

		try (Repository submoduleLocalRepo = generator.getRepository()) {
			JGitTestUtil.writeTrashFile(submoduleLocalRepo, "file.txt",
					"new data");
			Git.wrap(submoduleLocalRepo).commit().setAll(true)
					.setMessage("local commit").call();
