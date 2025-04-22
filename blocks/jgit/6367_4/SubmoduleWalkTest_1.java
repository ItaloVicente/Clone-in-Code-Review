
	@Test
	public void indexWithGitmodules() throws Exception {
		final ObjectId subId = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";

		final Config gitmodules = new Config();
		gitmodules.setString(CONFIG_SUBMODULE_SECTION
				"sub");
		gitmodules.setString(CONFIG_SUBMODULE_SECTION
		final RevBlob gitmodulesBlob = testDb.blob(gitmodules.toText());

		gitmodules.setString(CONFIG_SUBMODULE_SECTION
		writeTrashFile(DOT_GIT_MODULES

		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(subId);
			}
		});
		editor.add(new PathEdit(DOT_GIT_MODULES) {

			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.REGULAR_FILE);
				ent.setObjectId(gitmodulesBlob);
			}
		});
		editor.commit();

		SubmoduleWalk gen = SubmoduleWalk.forIndex(db);
		assertTrue(gen.next());
		assertEquals(path
		assertEquals(subId
		assertEquals(new File(db.getWorkTree()
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertEquals("sub"
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getRepository());
		assertFalse(gen.next());
	}

	@Test
	public void treeIdWithGitmodules() throws Exception {
		final ObjectId subId = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";

		final Config gitmodules = new Config();
		gitmodules.setString(CONFIG_SUBMODULE_SECTION
				"sub");
		gitmodules.setString(CONFIG_SUBMODULE_SECTION

		RevCommit commit = testDb.getRevWalk().parseCommit(testDb.commit()
				.noParents()
				.add(DOT_GIT_MODULES
				.edit(new PathEdit(path) {

							public void apply(DirCacheEntry ent) {
								ent.setFileMode(FileMode.GITLINK);
								ent.setObjectId(subId);
							}
						})
				.create());

		SubmoduleWalk gen = SubmoduleWalk.forPath(db
		assertEquals(path
		assertEquals(subId
		assertEquals(new File(db.getWorkTree()
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertEquals("sub"
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getRepository());
		assertFalse(gen.next());
	}

	@Test
	public void testTreeIteratorWithGitmodules() throws Exception {
		final ObjectId subId = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";

		final Config gitmodules = new Config();
		gitmodules.setString(CONFIG_SUBMODULE_SECTION
				"sub");
		gitmodules.setString(CONFIG_SUBMODULE_SECTION

		RevCommit commit = testDb.getRevWalk().parseCommit(testDb.commit()
				.noParents()
				.add(DOT_GIT_MODULES
				.edit(new PathEdit(path) {

							public void apply(DirCacheEntry ent) {
								ent.setFileMode(FileMode.GITLINK);
								ent.setObjectId(subId);
							}
						})
				.create());

		final CanonicalTreeParser p = new CanonicalTreeParser();
		p.reset(testDb.getRevWalk().getObjectReader()
		SubmoduleWalk gen = SubmoduleWalk.forPath(db
		assertEquals(path
		assertEquals(subId
		assertEquals(new File(db.getWorkTree()
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertEquals("sub"
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getRepository());
		assertFalse(gen.next());
	}
