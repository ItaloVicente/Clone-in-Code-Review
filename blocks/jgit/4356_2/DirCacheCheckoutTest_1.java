	@Test
	public void testRules1thru3_NoIndexEntry() throws IOException {
		Tree head = new Tree(db);
		head = buildTree(mk("foo"));
		ObjectId objectId = head.findBlobMember("foo").getId();
		Tree merge = new Tree(db);

		prescanTwoTrees(head

		assertTrue(getRemoved().contains("foo"));

		prescanTwoTrees(merge

		assertEquals(objectId

		merge = buildTree(mkmap("foo"
		ObjectId anotherId = merge.findBlobMember("foo").getId();

		prescanTwoTrees(head

		assertEquals(anotherId
	}

	void setupCase(HashMap<String
		theHead = buildTree(headEntries);
		theMerge = buildTree(mergeEntries);
		buildIndex(indexEntries);
	}

	private void buildIndex(HashMap<String
		dirCache = new DirCache(db.getIndexFile()
		if (indexEntries != null) {
			assertTrue(dirCache.lock());
			DirCacheEditor editor = dirCache.editor();
			for (java.util.Map.Entry<String
				writeTrashFile(e.getKey()
				ObjectInserter inserter = db.newObjectInserter();
				final ObjectId id = inserter.insert(Constants.OBJ_BLOB
						.getValue().getBytes(Constants.CHARSET));
				editor.add(new DirCacheEditor.DeletePath(e.getKey()));
				editor.add(new DirCacheEditor.PathEdit(e.getKey()) {
					@Override
					public void apply(DirCacheEntry ent) {
						ent.setFileMode(FileMode.REGULAR_FILE);
						ent.setObjectId(id);
						ent.setUpdateNeeded(false);
					}
				});
			}
			assertTrue(editor.commit());
		}

	}

	private Tree buildTree(HashMap<String
		Tree tree = new Tree(db);
		if (headEntries == null)
			return tree;
		FileTreeEntry fileEntry;
		Tree parent;
		ObjectInserter oi = db.newObjectInserter();
		try {
			for (java.util.Map.Entry<String
				fileEntry = tree.addFile(e.getKey());
				fileEntry.setId(genSha1(e.getValue()));
				parent = fileEntry.getParent();
				while (parent != null) {
					parent.setId(oi.insert(Constants.OBJ_TREE
					parent = parent.getParent();
				}
			}
			oi.flush();
		} finally {
			oi.release();
		}
		return tree;
	}

	ObjectId genSha1(String data) {
		ObjectInserter w = db.newObjectInserter();
		try {
			ObjectId id = w.insert(Constants.OBJ_BLOB
			w.flush();
			return id;
		} catch (IOException e) {
			fail(e.toString());
		} finally {
			w.release();
		}
		return null;
	}

	protected void go() throws IllegalStateException
		prescanTwoTrees(theHead
	}

	@Test
	public void testRules4thru13_IndexEntryNotInHead() throws IOException {
		HashMap<String

		idxMap = new HashMap<String
		idxMap.put("foo"
		setupCase(null
		go();

		assertTrue(getUpdated().isEmpty());
		assertTrue(getRemoved().isEmpty());
		assertTrue(getConflicts().isEmpty());

		idxMap = new HashMap<String
		idxMap.put("foo"
		setupCase(null
		go();

		assertAllEmpty();

		HashMap<String
		mergeMap = new HashMap<String

		mergeMap.put("foo"
		setupCase(null
		go();

		assertTrue(getUpdated().isEmpty());
		assertTrue(getRemoved().isEmpty());
		assertTrue(getConflicts().contains("foo"));


		HashMap<String
		headMap.put("foo"
		setupCase(headMap
		go();

		assertTrue(getRemoved().contains("foo"));
		assertTrue(getUpdated().isEmpty());
		assertTrue(getConflicts().isEmpty());

		setupCase(headMap
		assertTrue(new File(trash
		writeTrashFile("foo"
		db.readDirCache().getEntry(0).setUpdateNeeded(true);
		go();

		assertTrue(getRemoved().isEmpty());
		assertTrue(getUpdated().isEmpty());
		assertTrue(getConflicts().contains("foo"));

		headMap.put("foo"
		setupCase(headMap
		go();

		assertTrue(getRemoved().isEmpty());
		assertTrue(getUpdated().isEmpty());
		assertTrue(getConflicts().contains("foo"));

		setupCase(headMap
		go();

		assertAllEmpty();

		setupCase(headMap
		assertTrue(getConflicts().contains("foo"));

		setupCase(headMap
		assertAllEmpty();

		setupCase(idxMap
		assertTrue(getUpdated().containsKey("foo"));

		setupCase(idxMap
		assertTrue(new File(trash
		writeTrashFile("foo"
		db.readDirCache().getEntry(0).setUpdateNeeded(true);
		go();
		assertTrue(getConflicts().contains("foo"));
	}

	private void assertAllEmpty() {
		assertTrue(getRemoved().isEmpty());
		assertTrue(getUpdated().isEmpty());
		assertTrue(getConflicts().isEmpty());
	}

	@Test
	public void testDirectoryFileSimple() throws IOException {
		Tree treeDF = buildTree(mkmap("DF"
		Tree treeDFDF = buildTree(mkmap("DF/DF"
		buildIndex(mkmap("DF"

		prescanTwoTrees(treeDF

		assertTrue(getRemoved().contains("DF"));
		assertTrue(getUpdated().containsKey("DF/DF"));

		recursiveDelete(new File(trash
		buildIndex(mkmap("DF/DF"

		prescanTwoTrees(treeDFDF
		assertTrue(getRemoved().contains("DF/DF"));
		assertTrue(getUpdated().containsKey("DF"));
	}

	@Test
	public void testDirectoryFileConflicts_1() throws Exception {
		doit(mk("DF/DF")
		assertNoConflicts();
		assertUpdated("DF");
		assertRemoved("DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_2() throws Exception {
		setupCase(mk("DF/DF")
		writeTrashFile("DF/DF"
		go();
		assertConflict("DF/DF");

	}

	@Test
	public void testDirectoryFileConflicts_3() throws Exception {
		doit(mk("DF/DF")
		assertUpdated("DF/DF");
		assertRemoved("DF");
	}

	@Test
	public void testDirectoryFileConflicts_4() throws Exception {
		doit(mk("DF/DF")
		assertUpdated("DF/DF");
		assertRemoved("DF");

	}

	@Test
	public void testDirectoryFileConflicts_5() throws Exception {
		doit(mk("DF/DF")
		assertRemoved("DF/DF");

	}

	@Test
	public void testDirectoryFileConflicts_6() throws Exception {
		setupCase(mk("DF/DF")
		writeTrashFile("DF"
		go();
		assertRemoved("DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_7() throws Exception {
		doit(mk("DF")
		assertUpdated("DF");
		assertRemoved("DF/DF");

		cleanUpDF();
		setupCase(mk("DF/DF")
		go();
		assertRemoved("DF/DF/DF/DF/DF");
		assertUpdated("DF/DF");

		cleanUpDF();
		setupCase(mk("DF/DF")
		writeTrashFile("DF/DF/DF/DF/DF"
		go();
		assertConflict("DF/DF/DF/DF/DF");

	}

	@Test
	public void testDirectoryFileConflicts_8() throws Exception {
		setupCase(mk("DF")
		recursiveDelete(new File(db.getWorkTree()
		writeTrashFile("DF"
		go();
		assertConflict("DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_9() throws Exception {
		doit(mk("DF")
		assertRemoved("DF/DF");
		assertUpdated("DF");
	}

	@Test
	public void testDirectoryFileConflicts_10() throws Exception {
		cleanUpDF();
		doit(mk("DF")
		assertNoConflicts();
	}

	@Test
	public void testDirectoryFileConflicts_11() throws Exception {
		doit(mk("DF")
		assertConflict("DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_12() throws Exception {
		cleanUpDF();
		doit(mk("DF")
		assertRemoved("DF");
		assertUpdated("DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_13() throws Exception {
		cleanUpDF();
		setupCase(mk("DF")
		writeTrashFile("DF"
		go();
		assertConflict("DF");
		assertUpdated("DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_14() throws Exception {
		cleanUpDF();
		doit(mk("DF")
		assertConflict("DF");
		assertUpdated("DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_15() throws Exception {
		doit(mkmap()


		assertUpdated("DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_15b() throws Exception {
		doit(mkmap()


		assertUpdated("DF/DF/DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_16() throws Exception {
		cleanUpDF();
		doit(mkmap()
		assertRemoved("DF/DF/DF");
		assertUpdated("DF");
	}

	@Test
	public void testDirectoryFileConflicts_17() throws Exception {
		cleanUpDF();
		setupCase(mkmap()
		writeTrashFile("DF/DF/DF"
		go();
		assertConflict("DF/DF/DF");

	}

	@Test
	public void testDirectoryFileConflicts_18() throws Exception {
		cleanUpDF();
		doit(mk("DF/DF")
		assertRemoved("DF/DF");
		assertUpdated("DF/DF/DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_19() throws Exception {
		cleanUpDF();
		doit(mk("DF/DF/DF/DF")
		assertRemoved("DF/DF/DF/DF");
		assertUpdated("DF/DF/DF");
	}

	protected void cleanUpDF() throws Exception {
		tearDown();
		setUp();
		recursiveDelete(new File(trash
	}

	protected void assertConflict(String s) {
		assertTrue(getConflicts().contains(s));
	}

	protected void assertUpdated(String s) {
		assertTrue(getUpdated().containsKey(s));
	}

	protected void assertRemoved(String s) {
		assertTrue(getRemoved().contains(s));
	}

	protected void assertNoConflicts() {
		assertTrue(getConflicts().isEmpty());
	}

	protected void doit(HashMap<String
			throws IOException {
				setupCase(h
				go();
			}

	@Test
	public void testUntrackedConflicts() throws IOException {
		setupCase(null
		writeTrashFile("foo"
		go();

		recursiveDelete(new File(trash
		setupCase(mk("other")
				mk("other"));
		writeTrashFile("foo"
		try {
			checkout();
			fail("didn't get the expected exception");
		} catch (CheckoutConflictException e) {
			assertConflict("foo");
			assertWorkDir(mkmap("foo"
			assertIndex(mk("other"));
		}

		recursiveDelete(new File(trash
		recursiveDelete(new File(trash
		setupCase(null
		writeTrashFile("foo"
		try {
			checkout();
			fail("didn't get the expected exception");
		} catch (CheckoutConflictException e) {
			assertConflict("foo");
			assertWorkDir(mkmap("foo"
			assertIndex(mkmap("other"
		}


		recursiveDelete(new File(trash
		recursiveDelete(new File(trash
		setupCase(null
		writeTrashFile("foo/bar/baz"
		writeTrashFile("foo/blahblah"
		go();

		assertConflict("foo");
		assertConflict("foo/bar/baz");
		assertConflict("foo/blahblah");

		recursiveDelete(new File(trash

		setupCase(mkmap("foo/bar"
				mk("foo")
		assertTrue(new File(trash
		go();

		assertNoConflicts();
	}

	@Test
	public void testCloseNameConflictsX0() throws IOException {
		setupCase(mkmap("a/a"
		checkout();
		assertIndex(mkmap("a/a"
		assertWorkDir(mkmap("a/a"
		go();
		assertIndex(mkmap("a/a"
		assertWorkDir(mkmap("a/a"
		assertNoConflicts();
	}

	@Test
	public void testCloseNameConflicts1() throws IOException {
		setupCase(mkmap("a/a"
		checkout();
		assertIndex(mkmap("a/a"
		assertWorkDir(mkmap("a/a"
		go();
		assertIndex(mkmap("a/a"
		assertWorkDir(mkmap("a/a"
		assertNoConflicts();
	}

	@Test
	public void testCheckoutHierarchy() throws IOException {
		setupCase(
				mkmap("a"
						"e/g")
				mkmap("a"
						"e/g2")
				mkmap("a"
						"e/g3"));
		try {
			checkout();
		} catch (CheckoutConflictException e) {
			assertWorkDir(mkmap("a"
					"e/f"
			assertConflict("e/g");
		}
	}

	@Test
	public void testCheckoutOutChanges() throws IOException {
		setupCase(mk("foo")
		checkout();
		assertIndex(mk("foo/bar"));
		assertWorkDir(mk("foo/bar"));

		assertFalse(new File(trash
		assertTrue(new File(trash
		recursiveDelete(new File(trash

		assertWorkDir(mkmap());

		setupCase(mk("foo/bar")
		checkout();

		assertIndex(mk("foo"));
		assertWorkDir(mk("foo"));

		assertFalse(new File(trash
		assertTrue(new File(trash

		setupCase(mk("foo")

		assertIndex(mkmap("foo"
		assertWorkDir(mkmap("foo"

		try {
			checkout();
			fail("did not throw exception");
		} catch (CheckoutConflictException e) {
			assertIndex(mkmap("foo"
			assertWorkDir(mkmap("foo"
		}
	}

	@Test
	public void testCheckoutUncachedChanges() throws IOException {
		setupCase(mk("foo")
		writeTrashFile("foo"
		checkout();
		assertIndex(mk("foo"));
		assertWorkDir(mkmap("foo"
		assertTrue(new File(trash
	}

	@Test
	public void testDontOverwriteDirtyFile() throws IOException {
		setupCase(mk("foo")
		writeTrashFile("foo"
		try {
			checkout();
			fail("Didn't got the expected conflict");
		} catch (CheckoutConflictException e) {
			assertIndex(mk("foo"));
			assertWorkDir(mkmap("foo"
			assertTrue(getConflicts().equals(Arrays.asList("foo")));
			assertTrue(new File(trash
		}
	}

	public void assertWorkDir(HashMap<String
			IOException {
				TreeWalk walk = new TreeWalk(db);
				walk.setRecursive(true);
				walk.addTree(new FileTreeIterator(db));
				String expectedValue;
				String path;
				int nrFiles = 0;
				FileTreeIterator ft;
				while (walk.next()) {
					ft = walk.getTree(0
					path = ft.getEntryPathString();
					expectedValue = i.get(path);
					assertNotNull("found unexpected file for path "
							+ path + " in workdir"
					File file = new File(db.getWorkTree()
					assertTrue(file.exists());
					if (file.isFile()) {
						FileInputStream is = new FileInputStream(file);
						byte[] buffer = new byte[(int) file.length()];
						int offset = 0;
						int numRead = 0;
						while (offset < buffer.length
								&& (numRead = is.read(buffer
										- offset)) >= 0) {
							offset += numRead;
						}
						is.close();
						assertTrue("unexpected content for path " + path
								+ " in workDir. Expected: <" + expectedValue + ">"
								Arrays.equals(buffer
						nrFiles++;
					}
				}
				assertEquals("WorkDir has not the right size."
			}

