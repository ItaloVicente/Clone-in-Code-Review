	@Theory
	public void checkMergeConflictingSubmodulesWithoutIndex(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);
		writeTrashFile("initial"
		git.add().addFilepattern("initial").call();
		RevCommit initial = git.commit().setMessage("initial").call();

		Repository submodule = createWorkRepository();
		Git subgit = Git.wrap(submodule);
		JGitTestUtil.writeTrashFile(submodule
		RevCommit firstSubCommit = subgit.commit().setMessage("first submodule commit").call();

		writeSubmodule("one"
		git.add().addFilepattern(Constants.DOT_GIT_MODULES).call();
		RevCommit right = git.commit().setMessage("added one").call();

		JGitTestUtil.writeTrashFile(submodule
		RevCommit secondSubCommit = subgit.commit().setMessage("second submodule commit").call();

		git.checkout().setStartPoint(initial).setName("left").setCreateBranch(true).call();
		writeSubmodule("one"

		git.add().addFilepattern(Constants.DOT_GIT_MODULES).call();
		git.commit().setMessage("a different one").call();

		MergeResult result = git.merge().setStrategy(strategy)
				.include(right).call();

		Map<String
		assertEquals(1
		assertEquals(MergeFailureReason.SUBMODULE_CONFLICT
	}

	@Theory
	public void checkMergeNonConflictingSubmodulesWithoutIndex(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);
		writeTrashFile("initial"
		git.add().addFilepattern("initial").call();

		Repository submodule = createWorkRepository();
		Git subgit = Git.wrap(submodule);
		JGitTestUtil.writeTrashFile(submodule
		RevCommit firstSubCommit = subgit.commit().setMessage("first submodule commit").call();

		writeSubmodule("one"

		String existing = read(Constants.DOT_GIT_MODULES);
		String context = "\n# context\n# more context\n# yet more context\n";
		write(new File(db.getWorkTree()

		git.add().addFilepattern(Constants.DOT_GIT_MODULES).call();
		RevCommit initial = git.commit().setMessage("initial").call();

		writeSubmodule("two"
		git.add().addFilepattern(Constants.DOT_GIT_MODULES).call();

		RevCommit right = git.commit().setMessage("added one").call();

		git.checkout().setStartPoint(initial).setName("left").setCreateBranch(true).call();

		addSubmoduleToIndex("three"
		new File(db.getWorkTree()

		existing = read(Constants.DOT_GIT_MODULES);
		String three = "[submodule \"three\"]\n\tpath = three\n\turl = " +
				db.getDirectory().toURI() + "\n";
		write(new File(db.getWorkTree()

		git.add().addFilepattern(Constants.DOT_GIT_MODULES).call();
		git.commit().setMessage("a different one").call();

		MergeResult result = git.merge().setStrategy(strategy)
				.include(right).call();

		assertNull(result.getCheckoutConflicts());
		assertNull(result.getFailingPaths());
		for (String dir : Arrays.asList("one"
			assertTrue(new File(db.getWorkTree()
		}
	}

	private void writeSubmodule(String path
		addSubmoduleToIndex(path
		new File(db.getWorkTree()

		StoredConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
						.toString());
		config.save();

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.load();
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		modulesConfig.save();

	}

	private void addSubmoduleToIndex(String path
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new DirCacheEditor.PathEdit(path) {

			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(commit);
			}
		});
		editor.commit();
	}

