	@Test
	public void testFileModeSymLinkIsNotATree() throws IOException {
		org.junit.Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		FS fs = db.getFS();
		writeTrashFile("mål/data"
		fs.createSymLink(new File(trash
		FileTreeIterator fti = new FileTreeIterator(db);
		assertFalse(fti.eof());
		while (!fti.getEntryPathString().equals("länk")) {
			fti.next(1);
		}
		assertEquals("länk"
		assertEquals(FileMode.SYMLINK
		fti.next(1);
		assertFalse(fti.eof());
		assertEquals("mål"
		assertEquals(FileMode.TREE
		fti.next(1);
		assertTrue(fti.eof());
	}

	@Test
	public void testSymlinkNotModifiedThoughNormalized() throws Exception {
		DirCache dc = db.lockDirCache();
		DirCacheEditor dce = dc.editor();
		final String UNNORMALIZED = "target/";
		final byte[] UNNORMALIZED_BYTES = Constants.encode(UNNORMALIZED);
		try (ObjectInserter oi = db.newObjectInserter()) {
			final ObjectId linkid = oi.insert(Constants.OBJ_BLOB
					UNNORMALIZED_BYTES
			dce.add(new DirCacheEditor.PathEdit("link") {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.SYMLINK);
					ent.setObjectId(linkid);
					ent.setLength(UNNORMALIZED_BYTES.length);
				}
			});
			assertTrue(dce.commit());
		}
		try (Git git = new Git(db)) {
			git.commit().setMessage("Adding link").call();
			git.reset().setMode(ResetType.HARD).call();
			DirCacheIterator dci = new DirCacheIterator(db.readDirCache());
			FileTreeIterator fti = new FileTreeIterator(db);

			while (!fti.getEntryPathString().equals("link")) {
				fti.next(1);
			}
			assertEquals("link"
			assertEquals("link"

			assertFalse(fti.isModified(dci.getDirCacheEntry()
					db.newObjectReader()));
		}
	}

	@Test
	public void testSymlinkModifiedNotNormalized() throws Exception {
		DirCache dc = db.lockDirCache();
		DirCacheEditor dce = dc.editor();
		final String NORMALIZED = "target";
		final byte[] NORMALIZED_BYTES = Constants.encode(NORMALIZED);
		try (ObjectInserter oi = db.newObjectInserter()) {
			final ObjectId linkid = oi.insert(Constants.OBJ_BLOB
					NORMALIZED_BYTES
			dce.add(new DirCacheEditor.PathEdit("link") {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.SYMLINK);
					ent.setObjectId(linkid);
					ent.setLength(NORMALIZED_BYTES.length);
				}
			});
			assertTrue(dce.commit());
		}
		try (Git git = new Git(db)) {
			git.commit().setMessage("Adding link").call();
			git.reset().setMode(ResetType.HARD).call();
			DirCacheIterator dci = new DirCacheIterator(db.readDirCache());
			FileTreeIterator fti = new FileTreeIterator(db);

			while (!fti.getEntryPathString().equals("link")) {
				fti.next(1);
			}
			assertEquals("link"
			assertEquals("link"

			assertFalse(fti.isModified(dci.getDirCacheEntry()
					db.newObjectReader()));
		}
	}

	@Test
	public void testSymlinkActuallyModified() throws Exception {
		org.junit.Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		final String NORMALIZED = "target";
		final byte[] NORMALIZED_BYTES = Constants.encode(NORMALIZED);
		try (ObjectInserter oi = db.newObjectInserter()) {
			final ObjectId linkid = oi.insert(Constants.OBJ_BLOB
					NORMALIZED_BYTES
			DirCache dc = db.lockDirCache();
			DirCacheEditor dce = dc.editor();
			dce.add(new DirCacheEditor.PathEdit("link") {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.SYMLINK);
					ent.setObjectId(linkid);
					ent.setLength(NORMALIZED_BYTES.length);
				}
			});
			assertTrue(dce.commit());
		}
		try (Git git = new Git(db)) {
			git.commit().setMessage("Adding link").call();
			git.reset().setMode(ResetType.HARD).call();

			FileUtils.delete(new File(trash
			FS.DETECTED.createSymLink(new File(trash
			DirCacheIterator dci = new DirCacheIterator(db.readDirCache());
			FileTreeIterator fti = new FileTreeIterator(db);

			while (!fti.getEntryPathString().equals("link")) {
				fti.next(1);
			}
			assertEquals("link"
			assertEquals("link"

			assertTrue(fti.isModified(dci.getDirCacheEntry()
					db.newObjectReader()));
		}
	}
