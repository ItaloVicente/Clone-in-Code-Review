	@Test
	public void testBuildOneFile_Commit_FiresIndexChangedEvent()
			throws Exception {
		final String path = "a-file-path";
		final FileMode mode = FileMode.REGULAR_FILE;
		final long lastModified = 1218123387057L;
		final int length = 1342;
		final DirCacheEntry entOrig;
		final DirCache dc = db.lockDirCache();
		final String message = "received IndexChangedEvent";

		IndexChangedListener listener = new IndexChangedListener() {

			public void onIndexChanged(IndexChangedEvent event) {
				throw new RuntimeException(message);
			}
		};

		ListenerList l = db.getListenerList();
		l.addIndexChangedListener(listener);
		final DirCacheBuilder b = dc.builder();

		entOrig = new DirCacheEntry(path);
		entOrig.setFileMode(mode);
		entOrig.setLastModified(lastModified);
		entOrig.setLength(length);
		b.add(entOrig);
		try {
			b.commit();
		} catch (RuntimeException e) {
			assertEquals(e.getMessage()
			return;
		}
		fail("IndexChangedEvent wasn't sent");
	}

