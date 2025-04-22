	@Test
	public void repositoryWithRootLevelSubmoduleAbsoluteRef()
			throws IOException
		final ObjectId id = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";
		File dotGit = new File(db.getWorkTree()
				+ Constants.DOT_GIT);
		if (!dotGit.getParentFile().exists())
			dotGit.getParentFile().mkdirs();

		File modulesGitDir = new File(db.getDirectory()
				+ File.separatorChar + path);
		new FileWriter(dotGit).append(
				"gitdir: " + modulesGitDir.getAbsolutePath()).close();
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		builder.setWorkTree(new File(db.getWorkTree()
		builder.build().create();

		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(id);
			}
		});
		editor.commit();

		SubmoduleWalk gen = SubmoduleWalk.forIndex(db);
		assertTrue(gen.next());
		assertEquals(path
		assertEquals(id
		assertEquals(new File(db.getWorkTree()
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertNull(gen.getModulesPath());
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getModulesUrl());
		Repository subRepo = gen.getRepository();
		assertNotNull(subRepo);
		assertEquals(modulesGitDir
		assertEquals(new File(db.getWorkTree()
		assertFalse(gen.next());
	}

	@Test
	public void repositoryWithRootLevelSubmoduleRelativeRef()
			throws IOException
		final ObjectId id = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";
		File dotGit = new File(db.getWorkTree()
				+ Constants.DOT_GIT);
		if (!dotGit.getParentFile().exists())
			dotGit.getParentFile().mkdirs();

		File modulesGitDir = new File(db.getDirectory()
				+ File.separatorChar + path);
		new FileWriter(dotGit).append(
				"gitdir: " + "../" + Constants.DOT_GIT + "/modules/" + path)
				.close();
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		builder.setWorkTree(new File(db.getWorkTree()
		builder.build().create();

		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(id);
			}
		});
		editor.commit();

		SubmoduleWalk gen = SubmoduleWalk.forIndex(db);
		assertTrue(gen.next());
		assertEquals(path
		assertEquals(id
		assertEquals(new File(db.getWorkTree()
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertNull(gen.getModulesPath());
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getModulesUrl());
		Repository subRepo = gen.getRepository();
		assertNotNull(subRepo);
		assertEquals(modulesGitDir
		assertEquals(new File(db.getWorkTree()
		assertFalse(gen.next());
	}

