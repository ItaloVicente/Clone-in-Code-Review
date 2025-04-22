	@Test
	public void testReset() throws Exception {
		final DirCache dc = DirCache.newInCore();

		final FileMode mode = FileMode.REGULAR_FILE;
		final String[] paths = { "a."
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(mode);
		}

		final DirCacheBuilder b = dc.builder();
		for (int i = 0; i < ents.length; i++)
			b.add(ents[i]);
		b.finish();

		DirCacheIterator dci = new DirCacheIterator(dc);
		assertFalse(dci.eof());
		assertEquals("a."
		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("a"
		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("a0b"
		dci.next(1);
		assertTrue(dci.eof());

		dci.reset();
		assertFalse(dci.eof());
		assertEquals("a."
		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("a"
		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("a0b"
		dci.next(1);
		assertTrue(dci.eof());

		dci.back(1);
		assertFalse(dci.eof());
		assertEquals("a0b"
		dci.back(1);
		assertFalse(dci.eof());
		assertEquals("a"
		dci.back(1);
		assertFalse(dci.eof());
		assertEquals("a."
		assertTrue(dci.first());

		assertFalse(dci.eof());
		assertEquals("a."
		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("a"
		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("a0b"
		dci.next(1);
		assertTrue(dci.eof());

		dci.back(1);
		assertFalse(dci.eof());
		assertEquals("a0b"
		dci.back(1);
		assertFalse(dci.eof());
		assertEquals("a"

		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("a0b"
		dci.next(1);
		assertTrue(dci.eof());

		AbstractTreeIterator sti = dci.createSubtreeIterator(null);
		assertEquals("a/b"
		sti.next(1);
		assertEquals("a/c"
		sti.next(1);
		assertEquals("a/d"
		sti.back(2);
		assertEquals("a/b"

	}

	@Test
	public void testBackBug396127() throws Exception {
		final DirCache dc = DirCache.newInCore();

		final FileMode mode = FileMode.REGULAR_FILE;
		final String[] paths = { "git-gui/po/fr.po"
				"git_remote_helpers/git/repo.py" };
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(mode);
		}

		final DirCacheBuilder b = dc.builder();
		for (int i = 0; i < ents.length; i++)
			b.add(ents[i]);
		b.finish();

		DirCacheIterator dci = new DirCacheIterator(dc);
		assertFalse(dci.eof());
		assertEquals("git-gui"
		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("git_remote_helpers"
		dci.back(1);
		assertFalse(dci.eof());
		assertEquals("git-gui"
		dci.next(1);
		assertEquals("git_remote_helpers"
		dci.next(1);
		assertTrue(dci.eof());

	}

