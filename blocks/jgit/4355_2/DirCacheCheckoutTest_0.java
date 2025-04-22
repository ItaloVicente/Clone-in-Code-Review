
	public void assertIndex(HashMap<String
			throws CorruptObjectException
		String expectedValue;
		String path;
		DirCache read = DirCache.read(db.getIndexFile()

		assertEquals("Index has not the right size."
				read.getEntryCount());
		for (int j = 0; j < read.getEntryCount(); j++) {
			path = read.getEntry(j).getPathString();
			expectedValue = i.get(path);
			assertNotNull("found unexpected entry for path " + path
					+ " in index"
			assertTrue("unexpected content for path " + path
					+ " in index. Expected: <" + expectedValue + ">"
					Arrays.equals(db.open(read.getEntry(j).getObjectId())
							.getCachedBytes()
		}
	}

