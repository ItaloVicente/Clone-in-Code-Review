		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File file = writeTrashFile(fname
			git.add().addFilepattern(fname).call();
			git.commit().setMessage("Added file").call();

			FileUtils.delete(file);
			FileUtils.mkdir(file);
			assertTrue("File must be a directory now"
			assertWorkDir(mkmap(fname
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
