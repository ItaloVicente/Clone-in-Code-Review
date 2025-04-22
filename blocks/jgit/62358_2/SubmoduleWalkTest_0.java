
	@Test
	public void testTreeIteratorWithGitmodulesNameNotPath() throws Exception {
		final ObjectId subId = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";
		final String arbitraryName = "x";

		final Config gitmodules = new Config();
		gitmodules.setString(CONFIG_SUBMODULE_SECTION
				CONFIG_KEY_PATH
		gitmodules.setString(CONFIG_SUBMODULE_SECTION
				CONFIG_KEY_URL

		RevCommit commit = testDb.getRevWalk()
				.parseCommit(testDb.commit().noParents()
						.add(DOT_GIT_MODULES
						.edit(new PathEdit(path) {

							@Override
							public void apply(DirCacheEntry ent) {
								ent.setFileMode(FileMode.GITLINK);
								ent.setObjectId(subId);
							}
						}).create());

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
