
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
				DirCacheEntry entry = dc.getEntry("skip-worktree-set");
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
	public void testSparseCheckoutTest() throws Exception {
		final boolean[] isForce = new boolean[] { true };
		final boolean[] isConfigSparse = new boolean[] { true };
		final boolean[] isPathInSparseFile = new boolean[] { false
		final boolean[] isCreateSparseFile = new boolean[] { false
		final boolean[] isMerge = new boolean[] { false
		final boolean[] isIndex = new boolean[] { false
		final boolean[] isWork = new boolean[] { false
		final boolean[] isSkip = new boolean[] { false
		List<String> compares = new ArrayList<>();

		for (boolean force : isForce) {
			for (boolean createSparseFile : isCreateSparseFile) {
				for (boolean configSparse : isConfigSparse) {
					for (boolean pathInSparseFile : isPathInSparseFile) {
						for (boolean merge : isMerge) {
							for (boolean index : isIndex) {
								for (boolean work : isWork) {
									for (boolean skip : isSkip) {
										String compare = checkoutCompare(force
												configSparse
												pathInSparseFile
												work
										if (!compare.isEmpty()) {
											compares.add(compare);
										}
									}
								}
							}
						}
					}
				}
			}
		}

		assertTrue(StringUtils.join(compares
	}

	private String checkoutCompare(boolean force
			final boolean createSparseFile
			final boolean merge
			final boolean skip) throws Exception {
		final String mergeTag = "MERGE";
		final String msg = "force:" + force + " configSparse:" + configSparse
				+ " createSparseFile:"
				+ createSparseFile + " merge:" + merge + " index:" + index
				+ " work:" + work + " skip:" + skip + " pathInSparseFile:"
				+ pathInSparseFile;
		String compare = "";

		try (Git git = new Git(createRepository(false
				Git jgit = new Git(createRepository(false

			if (configSparse) {
				configureSparseCheckout(git);
				configureSparseCheckout(jgit);
			}

			File gitFile = new File(git.getRepository().getWorkTree()
			File jgitFile = new File(jgit.getRepository().getWorkTree()

			if (merge) {
				assertTrue(gitFile.createNewFile());
				try (FileWriter fw = new FileWriter(gitFile)) {
					fw.write(gitFile.getName());
				}
				git.add().addFilepattern(gitFile.getName()).call();

				assertTrue(jgitFile.createNewFile());
				try (FileWriter fw = new FileWriter(jgitFile)) {
					fw.write(jgitFile.getName());
				}

				jgit.add().addFilepattern(jgitFile.getName()).call();
			}

			git.commit().setMessage(mergeTag).setAllowEmpty(true).call();
			jgit.commit().setMessage(mergeTag).setAllowEmpty(true).call();

			git.tag().setName(mergeTag).call();
			jgit.tag().setName(mergeTag).call();

			if (index) {
				if (!gitFile.exists()) {
					assertTrue(gitFile.createNewFile());
					try (FileWriter fw = new FileWriter(gitFile)) {
						fw.write(gitFile.getName());
					}

					git.add().addFilepattern(gitFile.getName()).call();
				}

				if (!jgitFile.exists()) {
					assertTrue(jgitFile.createNewFile());
					try (FileWriter fw = new FileWriter(jgitFile)) {
						fw.write(jgitFile.getName());
					}

					jgit.add().addFilepattern(jgitFile.getName()).call();
				}

				if (skip) {
					turnOnSkipWorkingTreeFlag(git
					turnOnSkipWorkingTreeFlag(jgit
				}
			} else {
				if (gitFile.exists()) {
					git.rm().addFilepattern(gitFile.getName()).call();
				}
				if (jgitFile.exists()) {
					jgit.rm().addFilepattern(jgitFile.getName()).call();
				}
			}

			if (createSparseFile) {
				File gitSparseFile = createSparseFile(git);
				File jgitSparseFile = createSparseFile(jgit);

				if (pathInSparseFile) {
					try (FileWriter fw = new FileWriter(gitSparseFile)) {
						fw.write(gitFile.getName());
					}

					try (FileWriter fw = new FileWriter(jgitSparseFile)) {
						fw.write(jgitFile.getName());
					}
				} else {
					try (FileWriter fw = new FileWriter(gitSparseFile)) {
					}

					try (FileWriter fw = new FileWriter(jgitSparseFile)) {
					}
				}
			}

			if (work) {
				if (!gitFile.exists()) {
					assertTrue(gitFile.createNewFile());
					try (FileWriter fw = new FileWriter(gitFile)) {
						fw.write(gitFile.getName());
					}
				}

				if (!jgitFile.exists()) {
					assertTrue(jgitFile.createNewFile());
					try (FileWriter fw = new FileWriter(jgitFile)) {
						fw.write(jgitFile.getName());
					}
				}

			} else {
				gitFile.delete();
				jgitFile.delete();
			}

			boolean jgitResult = true;
			try {
				jgit.checkout().setName(mergeTag).setForce(force).call();
			} catch (org.eclipse.jgit.api.errors.CheckoutConflictException e) {
				jgitResult = false;
			}

			ProcessBuilder builder = FS.DETECTED.runInShell("git"
					new String[] { "checkout"
			File directory = git.getRepository().isBare()
					? git.getRepository().getDirectory()
					: git.getRepository().getWorkTree();
			builder.directory(directory);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayOutputStream err = new ByteArrayOutputStream();
			boolean gitResult = FS.DETECTED.runProcess(builder
					"") == 0 ? true : false;

			DirCache gitCache = DirCache.read(git.getRepository());
			DirCacheEntry gitEntry = gitCache.getEntry(gitFile.getName());

			DirCache jgitCache = DirCache.read(jgit.getRepository());
			DirCacheEntry jgitEntry = jgitCache.getEntry(jgitFile.getName());

			List<String> entryStatus = new ArrayList<>();
			boolean indexMismatch = (gitEntry == null && jgitEntry != null)
					|| (gitEntry != null && jgitEntry == null);

			if (jgitEntry != null && gitEntry != null) {


				if (gitEntry.isAssumeValid() != jgitEntry.isAssumeValid()) {
					entryStatus.add("Assume Invalid");
				}

				if (gitEntry.isIntentToAdd() != jgitEntry.isIntentToAdd()) {
					entryStatus.add("Intent To Add");
				}
				if (gitEntry.isAssumeValid() != jgitEntry.isAssumeValid()) {
					entryStatus.add("Assume Invalid");
				}

				if (gitEntry.isMerged() != jgitEntry.isMerged()) {
					entryStatus.add("Is Merged");
				}

				if (gitEntry.isSkipWorkTree() != jgitEntry.isSkipWorkTree()) {
					entryStatus.add("Skip Work Tree");
				}

				if (gitEntry.isUpdateNeeded() != jgitEntry.isUpdateNeeded()) {
					entryStatus.add("Is Update Needed");
				}

				if (gitEntry.getRawMode() != jgitEntry.getRawMode()) {
					entryStatus.add("Raw Mode");
				}

				if (!gitEntry.getObjectId().equals(jgitEntry.getObjectId())) {
					entryStatus.add("Object Id");
				}

				if (!Arrays.equals(gitEntry.getRawPath()
						jgitEntry.getRawPath())) {
					entryStatus.add("Raw Path");
				}

				if (gitEntry.getStage() != jgitEntry.getStage()) {
					entryStatus.add("Stage");
				}
			}

			Status gs = git.status().call();
			Status js = jgit.status().call();
			List<String> status = new ArrayList<>();

			if (!gs.getAdded().equals(js.getAdded())) {
				status.add("Added");
			}

			if (!gs.getChanged().equals(js.getChanged())) {
				status.add("Changed");
			}

			if (!gs.getConflicting().equals(js.getConflicting())) {
				status.add("Conflicting");
			}

			if (!gs.getMissing().equals(js.getMissing())) {
				status.add("Missing");
			}

			if (!gs.getModified().equals(js.getModified())) {
				status.add("Modified");
			}

			if (!gs.getRemoved().equals(js.getRemoved())) {
				status.add("Removed");
			}

			if (!gs.getConflictingStageState()
					.equals(js.getConflictingStageState())) {
				status.add("Conflicting State");
			}

			if (!gs.getUntracked().equals(js.getUntracked())) {
				status.add("Untracked");
			}

			if (!gs.getIgnoredNotInIndex().equals(js.getIgnoredNotInIndex())) {
				status.add("Ignored In Index");
			}

			if (!gs.getIgnoredNotInIndex().equals(js.getIgnoredNotInIndex())) {
				status.add("Ignored Not In Index");
			}

			if (!gs.getUntrackedFolders().equals(js.getUntrackedFolders())) {
				status.add("Untracked Folders");
			}

			if (gs.isClean() != js.isClean()) {
				status.add("Clean");
			}

			if (!gs.getUncommittedChanges()
					.equals(js.getUncommittedChanges())) {
				status.add("Uncommitted Changes");
			}

			if (!status.isEmpty() || !entryStatus.isEmpty()
					|| gitFile.exists() != jgitFile.exists()
					|| gitResult != jgitResult || indexMismatch) {

				if (gitResult != jgitResult) {
					compare = msg + String.format(" Result: git:%s jgit:%s"
							Boolean.valueOf(gitResult)
							Boolean.valueOf(jgitResult));
					compare = compare.replace("\n"
				} else {
					String fileStatus = "git:" + gitFile.exists() + " jgit:"
							+ jgitFile.exists();
					String indexStatus = "git:" + gitEntry + " jgit:"
							+ jgitEntry;
					compare = msg + String.format(
							" Status:%s
							status.toString()
							Boolean.valueOf(jgitResult)
							entryStatus.toString()
					compare = compare.replace("\n"
				}
			}
		}
		return compare;
	}

	private File createSparseFile(Git git) throws IOException {
		File infoDir = new File(git.getRepository().getDirectory()
		infoDir.mkdirs();
		File gitSparseFile = new File(git.getRepository().getDirectory()
				Constants.INFO_SPARSE_CHECKOUT);
		assertTrue(gitSparseFile.createNewFile());
		return gitSparseFile;
	}

	private void turnOnSkipWorkingTreeFlag(Git git
			throws CorruptObjectException
		DirCache cache = DirCache.read(git.getRepository());
		assertTrue(cache.lock());

		DirCacheEditor editor = cache.editor();
		editor.add(new DirCacheEditor.PathEdit(gitFile.getName()) {

			@Override
			public void apply(DirCacheEntry ent) {
				ent.setSkipWorkTree(true);

			}
		});
		editor.commit();
	}

	private void configureSparseCheckout(Git git) throws IOException {
		StoredConfig config = git.getRepository().getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
		config.save();
	}

