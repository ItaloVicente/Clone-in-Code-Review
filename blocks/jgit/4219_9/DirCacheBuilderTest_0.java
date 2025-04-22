	@Test
	public void testBuildOneFile_Commit_IndexChangedEvent()
			throws Exception {
		final class ReceivedEventMarkerException extends RuntimeException {
			private static final long serialVersionUID = 1L;
		}

		final String path = "a-file-path";
		final FileMode mode = FileMode.REGULAR_FILE;
		final long lastModified = 1218123387057L;
		final int length = 1342;
		DirCacheEntry entOrig;
		boolean receivedEvent = false;

		DirCache dc = db.lockDirCache();
		IndexChangedListener listener = new IndexChangedListener() {

			public void onIndexChanged(IndexChangedEvent event) {
				throw new ReceivedEventMarkerException();
			}
		};

		ListenerList l = db.getListenerList();
		l.addIndexChangedListener(listener);
		DirCacheBuilder b = dc.builder();

		entOrig = new DirCacheEntry(path);
		entOrig.setFileMode(mode);
		entOrig.setLastModified(lastModified);
		entOrig.setLength(length);
		b.add(entOrig);
		try {
			b.commit();
		} catch (ReceivedEventMarkerException e) {
			receivedEvent = true;
		}
		if (!receivedEvent)
			fail("did not receive IndexChangedEvent");

		dc = db.lockDirCache();
		listener = new IndexChangedListener() {

			public void onIndexChanged(IndexChangedEvent event) {
				throw new ReceivedEventMarkerException();
			}
		};

		l = db.getListenerList();
		l.addIndexChangedListener(listener);
		b = dc.builder();

		entOrig = new DirCacheEntry(path);
		entOrig.setFileMode(mode);
		entOrig.setLastModified(lastModified);
		entOrig.setLength(length);
		b.add(entOrig);
		try {
			b.commit();
		} catch (ReceivedEventMarkerException e) {
			fail("unexpected IndexChangedEvent");
		}
	}

