
	@Test
	public void testRejectInvalidWindowsPaths() throws Exception {
		SystemReader.setInstance(new MockSystemReader() {
			{
				setUnix();
			}
		});

		String path = "src/con.txt";
		DirCache dc = db.lockDirCache();
		DirCacheBuilder b = dc.builder();
		DirCacheEntry e = new DirCacheEntry(path);
		e.setFileMode(FileMode.REGULAR_FILE);
		e.setObjectId(new ObjectInserter.Formatter().idFor(
				Constants.OBJ_BLOB
				Constants.encode(path)));
		b.add(e);
		b.commit();
		db.readDirCache();

		SystemReader.setInstance(new MockSystemReader() {
			{
				setWindows();
			}
		});

		try {
			db.readDirCache();
			fail("should have rejected " + path);
		} catch (CorruptObjectException err) {
			assertEquals(MessageFormat.format(JGitText.get().invalidPath
					err.getMessage());
			assertNotNull(err.getCause());
			assertEquals("invalid name 'CON'"
		}
	}
