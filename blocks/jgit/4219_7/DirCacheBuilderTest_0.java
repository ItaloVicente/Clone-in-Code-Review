
	@Test
	public void testBuildOneFile_Commit_FiresIndexChangedEvent()
			throws Exception {
		final class ReceivedEventMarkerException extends RuntimeException {
			private static final long serialVersionUID = 1L;
		}

		final String path = "a-file-path";
		final FileMode mode = FileMode.REGULAR_FILE;
		final long lastModified = 1218123387057L;
		final int length = 1342;
		final DirCacheEntry entOrig;
		final DirCache dc = db.lockDirCache();

		IndexChangedListener listener = new IndexChangedListener() {

			public void onIndexChanged(IndexChangedEvent event) {
				throw new ReceivedEventMarkerException();
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
		} catch (ReceivedEventMarkerException e) {
			return;
		}
		fail("IndexChangedEvent wasn't sent");
	}

	@Test
	public void testUnchangedIndex_Commit_FiresNoIndexChangedEvent()
			throws Exception {
		final class ReceivedEventMarkerException extends RuntimeException {
			private static final long serialVersionUID = 1L;
		}

		final DirCache dc = db.lockDirCache();

		IndexChangedListener listener = new IndexChangedListener() {

			public void onIndexChanged(IndexChangedEvent event) {
				throw new ReceivedEventMarkerException();
			}
		};

		ListenerList l = db.getListenerList();
		l.addIndexChangedListener(listener);
		final DirCacheEditor e = dc.editor();

		try {
			e.commit();
		} catch (ReceivedEventMarkerException ex) {
			fail("unexpected event
		}
	}

