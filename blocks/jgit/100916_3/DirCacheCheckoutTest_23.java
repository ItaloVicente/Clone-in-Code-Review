		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File file = writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("commit1").call();
			assertFalse(db.getFS().canExecute(file));

			git.branchCreate().setName("b1").call();

			file = writeTrashFile("file.txt"
			db.getFS().setExecute(file
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("commit2").call();

			writeTrashFile("file.txt"
			db.getFS().setExecute(file
			git.add().addFilepattern("file.txt").call();

			writeTrashFile("file.txt"
			db.getFS().setExecute(file

			assertEquals("[file.txt
					indexState(CONTENT));
			assertWorkDir(mkmap("file.txt"
			recorder.assertNoEvent();

			git.checkout().setName("b1").call();
			assertEquals("[file.txt
					indexState(CONTENT));
			assertWorkDir(mkmap("file.txt"
			recorder.assertNoEvent();
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
