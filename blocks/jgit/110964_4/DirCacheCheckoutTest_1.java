
	@Test
	public void testSkippedCheckoutNoFileSparseFalse() throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			TestRepository<Repository> db_t = new TestRepository<>(db);
			db_t.getRepository().getConfig().setBoolean(
					ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			BranchBuilder master = db_t.branch("master");
			master.commit().add("f"
			assertFalse(new File(db.getWorkTree()
			git.checkout().setName("master").call();
			assertTrue(new File(db.getWorkTree()
			recorder.assertEvent(new String[] { "f" }
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testSkippedCheckoutNoFileSparseTrue() throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			TestRepository<Repository> db_t = new TestRepository<>(db);
			db_t.getRepository().getConfig().setBoolean(
					ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			BranchBuilder master = db_t.branch("master");
			master.commit().add("f"
			assertFalse(new File(db.getWorkTree()
			git.checkout().setName("master").call();
			assertTrue(new File(db.getWorkTree()
			recorder.assertEvent(new String[] { "f" }
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testSkippedCheckoutWithEmptyFileSparseTrue() throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File infoDir = new File(db.getDirectory()
			infoDir.mkdirs();
			File file = new File(db.getDirectory()
					Constants.INFO_SPARSE_CHECKOUT);
			file.createNewFile();

			TestRepository<Repository> db_t = new TestRepository<>(db);
			db_t.getRepository().getConfig().setBoolean(
					ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			BranchBuilder master = db_t.branch("master");
			master.commit().add("f"
			assertFalse(new File(db.getWorkTree()
			git.checkout().setName("master").call();
			assertTrue(new File(db.getWorkTree()
			recorder.assertEvent(new String[] { "f" }
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testSkippedCheckoutWithSparseTrue() throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File infoDir = new File(db.getDirectory()
			infoDir.mkdirs();
			File file = new File(db.getDirectory()
					Constants.INFO_SPARSE_CHECKOUT);
			file.createNewFile();

			try (FileWriter fw = new FileWriter(file)) {
				fw.write("file-to-checkout");
				fw.write("\n!file-to-not-checkout");
				fw.write("\nfile-does-not-exist");
			}

			TestRepository<Repository> db_t = new TestRepository<>(db);
			db_t.getRepository().getConfig().setBoolean(
					ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			BranchBuilder master = db_t.branch("master");
			master.commit().add("f"
			master.commit().add("file-to-checkout"
			master.commit().add("file-to-not-checkout"
					.create();
			assertFalse(new File(db.getWorkTree()
			assertFalse(
					new File(db.getWorkTree()
			assertFalse(new File(db.getWorkTree()
					.exists());
			git.checkout().setName("master").call();
			assertFalse(new File(db.getWorkTree()
			assertTrue(new File(db.getWorkTree()
			assertFalse(new File(db.getWorkTree()
					.exists());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

