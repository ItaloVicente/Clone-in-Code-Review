		Git git = Git.wrap(db);

		File file = writeTrashFile("file.txt", "a");
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("commit1").call();
		assertFalse(db.getFS().canExecute(file));

		git.branchCreate().setName("b1").call();

		db.getFS().setExecute(file, true);
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("commit2").call();

		Status status = git.status().call();
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getChanged().isEmpty());
		assertTrue(db.getFS().canExecute(file));

		git.checkout().setName("b1").call();

		status = git.status().call();
		assertTrue(status.getModified().isEmpty());
		assertTrue(status.getChanged().isEmpty());
		assertFalse(db.getFS().canExecute(file));
