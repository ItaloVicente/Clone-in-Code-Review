		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File file = writeTrashFile(fname
			git.add().addFilepattern(fname).call();
			git.commit().setMessage("create file").call();
			assertWorkDir(mkmap(fname
