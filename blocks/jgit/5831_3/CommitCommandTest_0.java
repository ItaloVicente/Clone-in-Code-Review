
	@Test
	public void commitUpdatesSmudgedEntries() throws Exception {
		Git git = new Git(db);

		writeTrashFile("file1.txt"
		writeTrashFile("file2.txt"
		writeTrashFile("file3.txt"

		assertNotNull(git.add().addFilepattern("file1.txt")
				.addFilepattern("file2.txt").addFilepattern("file3.txt")
				.addFilepattern("content2").call());
		RevCommit commit = git.commit().setMessage("add files").call();
		assertNotNull(commit);

		DirCache cache = db.readDirCache();
		int file1Size = cache.getEntry("file1.txt").getLength();
		int file2Size = cache.getEntry("file2.txt").getLength();
		int file3Size = cache.getEntry("file3.txt").getLength();
		ObjectId file2Id = cache.getEntry("file2.txt").getObjectId();
		ObjectId file3Id = cache.getEntry("file3.txt").getObjectId();
		assertTrue(file1Size > 0);
		assertTrue(file2Size > 0);
		assertTrue(file3Size > 0);

		cache = db.lockDirCache();
		cache.getEntry("file1.txt").setLength(0);
		cache.getEntry("file2.txt").setLength(0);
		cache.getEntry("file3.txt").setLength(0);
		cache.write();
		assertTrue(cache.commit());

		cache = db.readDirCache();
		assertEquals(0
		assertEquals(0
		assertEquals(0

		long indexTime = db.getIndexFile().lastModified();
		db.getIndexFile().setLastModified(indexTime - 5000);

		writeTrashFile("file1.txt"
		assertNotNull(git.commit().setMessage("edit file").setOnly("file1.txt")
				.call());

		cache = db.readDirCache();
		assertEquals(file1Size
		assertEquals(file2Size
		assertEquals(file3Size
		assertEquals(file2Id
		assertEquals(file3Id
	}
