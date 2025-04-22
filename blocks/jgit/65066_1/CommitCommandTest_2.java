		try (Git git = new Git(db)) {
			File file1 = writeTrashFile("file1.txt"
			assertTrue(file1.setLastModified(file1.lastModified() - 5000));
			File file2 = writeTrashFile("file2.txt"
			assertTrue(file2.setLastModified(file2.lastModified() - 5000));
			File file3 = writeTrashFile("file3.txt"
			assertTrue(file3.setLastModified(file3.lastModified() - 5000));

			assertNotNull(git.add().addFilepattern("file1.txt")
					.addFilepattern("file2.txt").addFilepattern("file3.txt").call());
			RevCommit commit = git.commit().setMessage("add files").call();
			assertNotNull(commit);

			DirCache cache = DirCache.read(db.getIndexFile()
			int file1Size = cache.getEntry("file1.txt").getLength();
			int file2Size = cache.getEntry("file2.txt").getLength();
			int file3Size = cache.getEntry("file3.txt").getLength();
			ObjectId file2Id = cache.getEntry("file2.txt").getObjectId();
			ObjectId file3Id = cache.getEntry("file3.txt").getObjectId();
			assertTrue(file1Size > 0);
			assertTrue(file2Size > 0);
			assertTrue(file3Size > 0);

			cache = DirCache.lock(db.getIndexFile()
			cache.getEntry("file1.txt").setLength(0);
			cache.getEntry("file2.txt").setLength(0);
			cache.getEntry("file3.txt").setLength(0);
			cache.write();
			assertTrue(cache.commit());

			cache = DirCache.read(db.getIndexFile()
			assertEquals(0
			assertEquals(0
			assertEquals(0

			long indexTime = db.getIndexFile().lastModified();
			db.getIndexFile().setLastModified(indexTime - 5000);

			write(file1
			assertTrue(file1.setLastModified(file1.lastModified() + 2500));
			assertNotNull(git.commit().setMessage("edit file").setOnly("file1.txt")
					.call());

			cache = db.readDirCache();
			assertEquals(file1Size
			assertEquals(file2Size
			assertEquals(file3Size
			assertEquals(file2Id
			assertEquals(file3Id
		}
