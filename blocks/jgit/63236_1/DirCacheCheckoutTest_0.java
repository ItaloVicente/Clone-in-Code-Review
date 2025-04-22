		DirCache dc = DirCache.read(git.getRepository());
		assertEquals(2
		assertEquals(fname
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(fname + "/dir/file1"
		assertEquals(FileMode.REGULAR_FILE

		ObjectId rootId;
		try (ObjectInserter ins = git.getRepository().newObjectInserter()) {
			rootId = dc.writeTree(ins);
			ins.flush();
		}
		try (ObjectReader reader = git.getRepository().newObjectReader()) {
			byte[] raw = reader.open(rootId).getCachedBytes();
			try {
				new ObjectChecker().checkTree(raw);
				fail("ObjectChecker accepts invalid tree");
			} catch (CorruptObjectException err) {
				assertEquals("duplicate entry names"
			}
		}

