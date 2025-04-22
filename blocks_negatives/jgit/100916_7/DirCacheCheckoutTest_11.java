		Git git = Git.wrap(db);

		File file = writeTrashFile("file.txt", "a");
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("commit1").call();
		assertFalse(db.getFS().canExecute(file));

		git.branchCreate().setName("b1").call();

		file = writeTrashFile("file.txt", "b");
		db.getFS().setExecute(file, true);
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("commit2").call();

		writeTrashFile("file.txt", "a");
		db.getFS().setExecute(file, false);
		git.add().addFilepattern("file.txt").call();

		writeTrashFile("file.txt", "c");
		db.getFS().setExecute(file, true);

		assertEquals("[file.txt, mode:100644, content:a]", indexState(CONTENT));
		assertWorkDir(mkmap("file.txt", "c"));

		git.checkout().setName("b1").call();
		assertEquals("[file.txt, mode:100644, content:a]", indexState(CONTENT));
		assertWorkDir(mkmap("file.txt", "c"));
