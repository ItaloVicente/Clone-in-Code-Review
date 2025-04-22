		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File file = writeTrashFile(fname
			git.add().addFilepattern(fname).call();
			git.commit().setMessage("Added file").call();

			assertWorkDir(mkmap(fname

			FileUtils.delete(file);

			writeTrashFile(fname + "/dir"
			git.add().addFilepattern(fname + "/dir/file1").call();

			writeTrashFile(fname + "/dir"

			assertTrue("File must be a directory now"
			assertFalse("Must not delete non empty directory"

			assertWorkDir(mkmap(fname + "/dir/file1"
					"d"));
			recorder.assertNoEvent();

			git.checkout().setStartPoint(Constants.HEAD).addPath(fname).call();
			assertWorkDir(mkmap(fname
			recorder.assertEvent(new String[] { fname }
			Status st = git.status().call();
			assertTrue(st.isClean());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
