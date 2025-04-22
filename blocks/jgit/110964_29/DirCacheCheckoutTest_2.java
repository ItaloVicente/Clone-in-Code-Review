
	@Test
	public void testCheckoutWithNoFileAndSparseFalse() throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			TestRepository<Repository> db_t = new TestRepository<>(db);
			StoredConfig config = db_t.getRepository().getConfig();
			config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			config.save();
			BranchBuilder master = db_t.branch("master");
			master.commit().add("f"
			File file = new File(db.getWorkTree()
			assertFalse(file.exists());
			git.checkout().setName("master").call();
			assertTrue(file.exists());
			recorder.assertEvent(new String[] { "f" }
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testCheckoutNoFileAndSparseTrue() throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);

			TestRepository<Repository> db_t = new TestRepository<>(db);
			StoredConfig config = db_t.getRepository().getConfig();
			config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			config.save();

			BranchBuilder master = db_t.branch("master");
			master.commit().add("f"
			File file = new File(db.getWorkTree()
			assertFalse(file.exists());
			git.checkout().setName("master").call();
			assertTrue(file.exists());
			recorder.assertEvent(new String[] { "f" }
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testCheckoutWithEmptyFileAndSparseTrue() throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File infoDir = new File(db.getDirectory()
			infoDir.mkdirs();
			File infoFile = new File(db.getDirectory()
					Constants.INFO_SPARSE_CHECKOUT);
			infoFile.createNewFile();

			TestRepository<Repository> db_t = new TestRepository<>(db);

			StoredConfig config = db_t.getRepository().getConfig();
			config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			config.save();

			BranchBuilder master = db_t.branch("master");
			master.commit().add("f"
			File file = new File(db.getWorkTree()
			assertFalse(file.exists());
			git.checkout().setName("master").call();
			assertTrue(file.exists());
			recorder.assertEvent(new String[] { "f" }
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testCheckoutWithCommentInFileAndSparseTrue() throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File infoDir = new File(db.getDirectory()
			infoDir.mkdirs();
			File infoFile = new File(db.getDirectory()
					Constants.INFO_SPARSE_CHECKOUT);
			infoFile.createNewFile();

			try (FileWriter fw = new FileWriter(infoFile)) {
				fw.write("#comment");
			}

			TestRepository<Repository> db_t = new TestRepository<>(db);

			StoredConfig config = db_t.getRepository().getConfig();
			config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			config.save();

			BranchBuilder master = db_t.branch("master");
			File file = new File(db.getWorkTree()
			master.commit().add("f"
			assertFalse(file.exists());
			git.checkout().setName("master").call();
			assertTrue(file.exists());
			recorder.assertEvent(new String[] { "f" }
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testCheckoutWithEmptyLineInFileAndSparseTrue()
			throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File infoDir = new File(db.getDirectory()
			infoDir.mkdirs();
			File sparseFile = new File(db.getDirectory()
					Constants.INFO_SPARSE_CHECKOUT);
			sparseFile.createNewFile();

			try (FileWriter fw = new FileWriter(sparseFile)) {
				fw.write("");
				fw.write("\n");
			}

			TestRepository<Repository> db_t = new TestRepository<>(db);
			StoredConfig config = db_t.getRepository().getConfig();
			config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			config.save();

			BranchBuilder master = db_t.branch("master");
			master.commit().add("f"
			File file = new File(db.getWorkTree()
			assertFalse(file.exists());
			git.checkout().setName("master").call();
			assertTrue(file.exists());
			recorder.assertEvent(new String[] { "f" }
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testCheckoutWithSparseTrue() throws Exception {
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
				fw.write("checkout");
				fw.write("\n!not-checkout");
				fw.write("\nfile-does-not-exist");
				fw.write("\n#comment.txt");
				fw.write("\nnegated-a");
				fw.write("\n!negated-a");
				fw.write("\n!negated-b");
				fw.write("\nnegated-b");
				fw.write("\n!none-indexed-negated");
				fw.write("\nnone-indexed");
			}

			TestRepository<Repository> db_t = new TestRepository<>(db);
			StoredConfig config = db_t.getRepository().getConfig();
			config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			config.save();

			BranchBuilder master = db_t.branch("master");
			master.commit().add("checkout"
			master.commit().add("not-checkout"
			master.commit().add("comment.txt"
			master.commit().add("negated-a"
			master.commit().add("negated-b"

			File noneIndexedFileNegated = new File(db.getWorkTree()
					"none-indexed-negated");
			noneIndexedFileNegated.createNewFile();
			File noneIndexedFile = new File(db.getWorkTree()
			noneIndexedFile.createNewFile();

			File fileToCheckout = new File(db.getWorkTree()
			File fileToNotCheckout = new File(db.getWorkTree()
			File fileComment = new File(db.getWorkTree()
			File fileNegatedA = new File(db.getWorkTree()
			File fileNegatedB = new File(db.getWorkTree()
			File fileNotIndexedNegated = new File(db.getWorkTree()
					"none-indexed-negated");
			File fileNotIndexed = new File(db.getWorkTree()

			assertFalse(fileToCheckout.exists());
			assertFalse(fileToNotCheckout.exists());
			assertFalse(fileComment.exists());
			assertFalse(fileNegatedA.exists());
			assertFalse(fileNegatedB.exists());
			assertTrue(fileNotIndexedNegated.exists());
			assertTrue(fileNotIndexed.exists());

			git.checkout().setName("master").call();

			assertTrue(fileNotIndexedNegated.exists());
			assertTrue(fileNotIndexed.exists());
			assertTrue(fileToCheckout.exists());
			assertFalse(fileToNotCheckout.exists());
			assertFalse(fileComment.exists());
			assertFalse(fileNegatedA.exists());
			assertTrue(fileNegatedB.exists());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testCheckoutToggleSparseCheckoutInConfig() throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File infoDir = new File(db.getDirectory()
			infoDir.mkdirs();
			File sparseFile = new File(db.getDirectory()
					Constants.INFO_SPARSE_CHECKOUT);
			sparseFile.createNewFile();

			try (FileWriter fw = new FileWriter(sparseFile)) {
				fw.write("checkout");
				fw.write("\n!not-checkout");
			}

			TestRepository<Repository> db_t = new TestRepository<>(db);
			StoredConfig config = db_t.getRepository().getConfig();
			config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			config.save();

			BranchBuilder master = db_t.branch("master");
			master.commit().add("checkout"
			master.commit().add("not-checkout"

			File fileToCheckout = new File(db.getWorkTree()
			File fileToNotCheckout = new File(db.getWorkTree()
			assertFalse(fileToCheckout.exists());
			assertFalse(fileToNotCheckout.exists());

			git.checkout().setName("master").call();

			assertTrue(fileToCheckout.exists());
			assertFalse(fileToNotCheckout.exists());

			try (FileWriter fw = new FileWriter(sparseFile
			}

			git.checkout().setName("master").call();
			assertTrue(fileToCheckout.exists());
			assertTrue(fileToNotCheckout.exists());

			config = db_t.getRepository().getConfig();
			config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			config.save();
			sparseFile.delete();

			git.checkout().setName("master").call();
			assertTrue(fileToCheckout.exists());
			assertTrue(fileToNotCheckout.exists());

		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testSparseCheckoutFileWithSkipWorkTreeSet() throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);

			TestRepository<Repository> db_t = new TestRepository<>(db);
			StoredConfig config = db_t.getRepository().getConfig();
			config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			config.save();

			BranchBuilder master = db_t.branch("master");
			master.commit().add("skip-worktree-set"
					.create();
			master.commit().add("skip-worktree-unset"
					.create();

			File fileSet = new File(db.getWorkTree()
			File fileUnSet = new File(db.getWorkTree()
			assertFalse(fileSet.exists());
			assertFalse(fileUnSet.exists());

			git.checkout().setName("master").call();

			DirCache dc = DirCache.read(db_t.getRepository());
			if (dc.lock()) {
				dc.read();
				DirCacheEntry entry = dc
						.getEntry("skip-worktree-set");
				entry.setSkipWorkTree(true);
				dc.write();
				dc.commit();
			} else {
				fail("Unable to lock index file: '"
						+ db.getIndexFile().getPath() + "'");
			}

			assertTrue(fileSet.exists());
			assertTrue(fileUnSet.exists());

			config = db_t.getRepository().getConfig();
			config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			config.save();

			File infoDir = new File(db.getDirectory()
			infoDir.mkdirs();
			File file = new File(db.getDirectory()
					Constants.INFO_SPARSE_CHECKOUT);
			file.createNewFile();

			try (FileWriter fw = new FileWriter(file)) {
				fw.write("skip-worktree-unset");
			}

			git.checkout().setName("master").call();
			assertFalse(fileSet.exists());
			assertTrue(fileUnSet.exists());

		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testSparseCheckoutWorkingTreeFileWithSkipWorkTreeSet()
			throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);

			TestRepository<Repository> db_t = new TestRepository<>(db);

			BranchBuilder master = db_t.branch("master");
			master.commit().add("modified"
					.create();
			master.commit().add("removed"
					.create();
			master.commit().add("unchanged"
					.create();

			File fileModified = new File(db.getWorkTree()
			File fileRemoved = new File(db.getWorkTree()
			File file = new File(db.getWorkTree()

			assertFalse(fileModified.exists());
			assertFalse(fileRemoved.exists());
			assertFalse(file.exists());

			git.checkout().setName("master").call();

			DirCache dc = DirCache.read(db_t.getRepository());
			if (dc.lock()) {
				dc.read();
				DirCacheEntry entry = dc.getEntry("modified");
				entry.setSkipWorkTree(true);

				entry = dc.getEntry("removed");
				entry.setSkipWorkTree(true);

				entry = dc.getEntry("unchanged");
				entry.setSkipWorkTree(true);
				dc.write();
				dc.commit();
			} else {
				fail("Unable to lock index file: '"
						+ db.getIndexFile().getPath() + "'");
			}

			assertTrue(fileRemoved.exists());
			assertTrue(fileModified.exists());
			assertTrue(file.exists());
			assertTrue(fileRemoved.delete());

			StoredConfig config = db_t.getRepository().getConfig();
			config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			config.save();

			File infoDir = new File(db.getDirectory()
			infoDir.mkdirs();
			File sparseFile = new File(db.getDirectory()
					Constants.INFO_SPARSE_CHECKOUT);
			sparseFile.createNewFile();

			try (FileWriter fw = new FileWriter(sparseFile)) {
			}

			try (FileWriter fw = new FileWriter(fileModified)) {
				fw.write("file-modified");
			}

			git.checkout().setName("master").call();
			assertTrue(fileModified.exists());
			assertTrue(fileRemoved.exists());
			assertTrue(file.exists());

			dc = DirCache.read(db_t.getRepository());
			if (dc.lock()) {
				dc.read();
				DirCacheEntry modifiedEntry = dc.getEntry("modified");
				DirCacheEntry removedEntry = dc.getEntry("removed");
				DirCacheEntry entry = dc.getEntry("unchanged");
				dc.unlock();

				assertFalse(removedEntry.isSkipWorkTree());
				assertFalse(entry.isSkipWorkTree());
				assertTrue(modifiedEntry.isSkipWorkTree());
			} else {
				fail("Unable to lock index file: '"
						+ db.getIndexFile().getPath() + "'");
			}

		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testSparseCheckoutOfUnMergedNotInWorkingTree()
			throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);

			TestRepository<Repository> db_t = new TestRepository<>(db);
			BranchBuilder master = db_t.branch("master");
			master.commit().create();

			File file = new File(db.getWorkTree()

			DirCache dc = DirCache.read(db_t.getRepository());
			if (dc.lock()) {
				DirCacheBuilder builder = dc.builder();
				DirCacheEntry unMergedEntry = new DirCacheEntry(file.getName()
						DirCacheEntry.STAGE_2);
				unMergedEntry.setFileMode(FileMode.REGULAR_FILE);
				unMergedEntry.setSkipWorkTree(true);
				builder.add(unMergedEntry);
				builder.finish();
				dc.write();
				dc.commit();
			} else {
				fail("Unable to lock index file: '"
						+ db.getIndexFile().getPath() + "'");
			}

			assertFalse(file.exists());

			StoredConfig config = db_t.getRepository().getConfig();
			config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			config.save();

			File infoDir = new File(db.getDirectory()
			infoDir.mkdirs();
			File sparseFile = new File(db.getDirectory()
					Constants.INFO_SPARSE_CHECKOUT);
			sparseFile.createNewFile();

			try (FileWriter fw = new FileWriter(sparseFile)) {
			}

			git.checkout().setName("master").call();
			assertFalse(file.exists());

			try (FileWriter fw = new FileWriter(sparseFile)) {
				fw.write(file.getName());
			}
			git.checkout().setName("master").setForce(true).call();
			assertFalse(file.exists());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testSparseCheckoutOfUnMergedInWorkingTree()
			throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);

			TestRepository<Repository> db_t = new TestRepository<>(db);
			BranchBuilder master = db_t.branch("master");
			master.commit().create();

			File file = new File(db.getWorkTree()

			DirCache dc = DirCache.read(db_t.getRepository());
			if (dc.lock()) {
				DirCacheBuilder builder = dc.builder();
				DirCacheEntry unMergedEntry = new DirCacheEntry(file.getName()
						DirCacheEntry.STAGE_2);
				unMergedEntry.setFileMode(FileMode.REGULAR_FILE);
				unMergedEntry.setSkipWorkTree(true);
				builder.add(unMergedEntry);
				builder.finish();
				dc.write();
				dc.commit();
			} else {
				fail("Unable to lock index file: '"
						+ db.getIndexFile().getPath() + "'");
			}

			assertFalse(file.exists());

			StoredConfig config = db_t.getRepository().getConfig();
			config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			config.save();

			File infoDir = new File(db.getDirectory()
			infoDir.mkdirs();
			File sparseFile = new File(db.getDirectory()
					Constants.INFO_SPARSE_CHECKOUT);
			sparseFile.createNewFile();

			try (FileWriter fw = new FileWriter(sparseFile)) {
			}

			file.createNewFile();
			assertTrue(file.exists());

			git.checkout().setName("master").setForce(true).call();
			assertFalse(file.exists());

			try (FileWriter fw = new FileWriter(sparseFile
				fw.write("");
			}

			git.checkout().setName("master").setForce(true).call();
			assertFalse(file.exists());

		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testSparseCheckoutOfUnMergedInWorkingTreeFlagNotSet()
			throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);

			TestRepository<Repository> db_t = new TestRepository<>(db);
			BranchBuilder master = db_t.branch("master");
			master.commit().create();

			File fileA = new File(db.getWorkTree()
			File fileB = new File(db.getWorkTree()

			assertFalse(fileA.exists());
			assertFalse(fileB.exists());

			StoredConfig config = db_t.getRepository().getConfig();
			config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			config.save();

			File infoDir = new File(db.getDirectory()
			infoDir.mkdirs();
			File sparseFile = new File(db.getDirectory()
					Constants.INFO_SPARSE_CHECKOUT);
			sparseFile.createNewFile();

			try (FileWriter fw = new FileWriter(sparseFile)) {
			}

			fileB.createNewFile();
			assertTrue(fileB.exists());

			master.commit().add(fileA.getName()
			git.checkout().setName("master").setForce(true).call();
			assertTrue(fileA.exists());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testSparseCheckoutWithWorkingTreeFlagSet() throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);

			TestRepository<Repository> db_t = new TestRepository<>(db);
			BranchBuilder master = db_t.branch("master");
			master.commit().create();

			File fileA = new File(db.getWorkTree()

			DirCache dc = DirCache.read(db_t.getRepository());
			if (dc.lock()) {
				DirCacheBuilder builder = dc.builder();
				DirCacheEntry unMergedEntry = new DirCacheEntry(fileA.getName()
						DirCacheEntry.STAGE_2);
				unMergedEntry.setFileMode(FileMode.REGULAR_FILE);
				unMergedEntry.setSkipWorkTree(true);
				builder.add(unMergedEntry);
				builder.finish();
				dc.write();
				dc.commit();
			} else {
				fail("Unable to lock index file: '"
						+ db.getIndexFile().getPath() + "'");
			}

			assertFalse(fileA.exists());

			StoredConfig config = db_t.getRepository().getConfig();
			config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
			config.save();

			File infoDir = new File(db.getDirectory()
			infoDir.mkdirs();
			File sparseFile = new File(db.getDirectory()
					Constants.INFO_SPARSE_CHECKOUT);
			sparseFile.createNewFile();

			master.commit().add(fileA.getName()
			git.checkout().setName("master").setForce(true).call();
			assertFalse(fileA.exists());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

