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

			db.getFS().setExecute(file
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("commit2").call();
			recorder.assertNoEvent();

			Status status = git.status().call();
			assertTrue(status.getModified().isEmpty());
			assertTrue(status.getChanged().isEmpty());
			assertTrue(db.getFS().canExecute(file));

			git.checkout().setName("b1").call();

			status = git.status().call();
			assertTrue(status.getModified().isEmpty());
			assertTrue(status.getChanged().isEmpty());
			assertFalse(db.getFS().canExecute(file));
			recorder.assertEvent(new String[] { "file.txt" }
					ChangeRecorder.EMPTY);
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
