		Git git = Git.wrap(db);

		File file = writeTrashFile("file.txt", "a");
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("commit1").call();
		assertFalse(db.getFS().canExecute(file));

		git.branchCreate().setName("b1").call();

		writeTrashFile("file2.txt", "");
		git.add().addFilepattern("file2.txt").call();
		git.commit().setMessage("commit2").call();

		writeTrashFile("file.txt", "a");
		db.getFS().setExecute(file, true);
		git.add().addFilepattern("file.txt").call();

		writeTrashFile("file.txt", "b");

		assertEquals(
				"[file.txt, mode:100755, content:a][file2.txt, mode:100644, content:]",
				indexState(CONTENT));
		assertWorkDir(mkmap("file.txt", "b", "file2.txt", ""));
