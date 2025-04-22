		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File file1 = writeTrashFile("file1.txt"
			git.add().addFilepattern("file1.txt").call();
			git.commit().setMessage("commit1").call();
			assertFalse(db.getFS().canExecute(file1));

			File file2 = writeTrashFile("file2.txt"
			git.add().addFilepattern("file2.txt").call();
			git.commit().setMessage("commit2").call();
			assertFalse(db.getFS().canExecute(file2));
			recorder.assertNoEvent();

			assertNotNull(git.checkout().setCreateBranch(true).setName("b1")
					.setStartPoint(Constants.HEAD + "~1").call());
			recorder.assertEvent(ChangeRecorder.EMPTY
					new String[] { "file2.txt" });

			file1 = writeTrashFile("file1.txt"
			db.getFS().setExecute(file1
			git.add().addFilepattern("file1.txt").call();

			assertNotNull(git.checkout().setName(Constants.MASTER).call());
			recorder.assertEvent(new String[] { "file2.txt" }
					ChangeRecorder.EMPTY);
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
