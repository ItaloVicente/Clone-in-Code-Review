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

			Status status = git.status().call();
			assertTrue(status.getModified().isEmpty());
			assertTrue(status.getChanged().isEmpty());
			assertTrue(db.getFS().canExecute(file));

			writeTrashFile("file.txt"

			CheckoutCommand checkout = git.checkout().setName("b1");
			try {
				checkout.call();
				fail("Checkout exception not thrown");
			} catch (org.eclipse.jgit.api.errors.CheckoutConflictException e) {
				CheckoutResult result = checkout.getResult();
				assertNotNull(result);
				assertNotNull(result.getConflictList());
				assertEquals(1
				assertTrue(result.getConflictList().contains("file.txt"));
			}
			recorder.assertNoEvent();
		} finally {
			if (handle != null) {
				handle.remove();
			}
