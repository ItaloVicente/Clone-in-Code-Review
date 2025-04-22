	@Test
	public void testAddGitlink() throws Exception {
		createNestedRepo("git-link-dir");
		try (Git git = new Git(db)) {
			git.add().addFilepattern("git-link-dir").call();

			assertEquals(
					"[git-link-dir
					indexState(0));
			Set<String> untrackedFiles = git.status().call().getUntracked();
			assert (untrackedFiles.isEmpty());
		}

	}

	@Test
	public void testAddSubrepoWithDirNoGitlinks() throws Exception {
		createNestedRepo("nested-repo");

		StoredConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_DIRNOGITLINKS
		config.save();

		assert (db.getConfig().get(WorkingTreeOptions.KEY).isDirNoGitLinks());

		try (Git git = new Git(db)) {
			git.add().addFilepattern("nested-repo").call();

			assertEquals(
					"[nested-repo/README1.md
							"[nested-repo/README2.md
					indexState(0));
		}

		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_DIRNOGITLINKS
		config.save();

		File readme3 = new File(db.getWorkTree()
		FileUtils.createNewFile(readme3);
		PrintWriter writer = new PrintWriter(readme3);
		writer.print("content");
		writer.close();

		try (Git git = new Git(db)) {
			git.add().addFilepattern("nested-repo").call();

			assertEquals(
					"[nested-repo/README1.md
							"[nested-repo/README2.md
							"[nested-repo/README3.md
					indexState(0));
		}
	}

	@Test
	public void testAddGitlinkDoesNotChange() throws Exception {
		createNestedRepo("nested-repo");

		try (Git git = new Git(db)) {
			git.add().addFilepattern("nested-repo").call();

			assertEquals(
					"[nested-repo
					indexState(0));
		}

		StoredConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_DIRNOGITLINKS
		config.save();

		assert (db.getConfig().get(WorkingTreeOptions.KEY).isDirNoGitLinks());

		try (Git git = new Git(db)) {
			git.add().addFilepattern("nested-repo").call();

			assertEquals(
					"[nested-repo
					indexState(0));
		}
	}

