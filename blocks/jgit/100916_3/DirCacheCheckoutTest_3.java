		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			writeTrashFile(fname
			git.add().addFilepattern(fname).call();

			String linkName = "link";
			File link = writeLink(linkName
			git.add().addFilepattern(linkName).call();
			git.commit().setMessage("Added file and link").call();

			assertWorkDir(mkmap(linkName

			FileUtils.delete(link);
			FileUtils.mkdir(link);
			assertTrue("Link must be a directory now"

			writeTrashFile(fname
			assertWorkDir(mkmap(fname
			recorder.assertNoEvent();

			git.checkout().setStartPoint(Constants.HEAD).addPath(fname)
					.addPath(linkName).call();

			assertWorkDir(mkmap(fname
			recorder.assertEvent(new String[] { fname
					new String[0]);

			Status st = git.status().call();
			assertTrue(st.isClean());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
