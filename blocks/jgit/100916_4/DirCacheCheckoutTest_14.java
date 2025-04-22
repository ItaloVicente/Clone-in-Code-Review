		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			writeTrashFile(fname
			git.add().addFilepattern(fname).call();
			git.commit().setMessage("create file").call();

			git.branchCreate().setName("side").call();

			writeTrashFile(fname
			git.add().addFilepattern(fname).call();
			git.commit().setMessage("modify file").call();
			recorder.assertNoEvent();

			git.checkout().setName("side").call();
			recorder.assertEvent(new String[] { fname }
			git.rm().addFilepattern(fname).call();
			recorder.assertEvent(new String[0]
			writeTrashFile(".gitignore"
			git.add().addFilepattern(".gitignore").call();
			git.commit().setMessage("delete and ignore file").call();

			writeTrashFile(fname
			recorder.assertNoEvent();
			git.checkout().setName("master").call();
			assertWorkDir(mkmap(fname
			recorder.assertEvent(new String[] { fname }
					new String[] { ".gitignore" });
			assertTrue(git.status().call().isClean());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
