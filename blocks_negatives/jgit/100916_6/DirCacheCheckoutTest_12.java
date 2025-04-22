		Git git = Git.wrap(db);

		File file1 = writeTrashFile("file1.txt", "a");
		git.add().addFilepattern("file1.txt").call();
		git.commit().setMessage("commit1").call();
		assertFalse(db.getFS().canExecute(file1));

		File file2 = writeTrashFile("file2.txt", "b");
		git.add().addFilepattern("file2.txt").call();
		git.commit().setMessage("commit2").call();
		assertFalse(db.getFS().canExecute(file2));

		assertNotNull(git.checkout().setCreateBranch(true).setName("b1")
				.setStartPoint(Constants.HEAD + "~1").call());

		file1 = writeTrashFile("file1.txt", "c");
		db.getFS().setExecute(file1, true);
		git.add().addFilepattern("file1.txt").call();

		assertNotNull(git.checkout().setName(Constants.MASTER).call());
