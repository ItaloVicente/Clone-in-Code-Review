	public void testUnsupportedOptionalExtension() throws Exception {
		final DirCache dc = new DirCache(pathOf("gitgit.index.ZZZZ"));
		dc.read();
		assertEquals(1
		assertEquals("A"
	}

	public void testUnsupportedRequiredExtension() throws Exception {
		final DirCache dc = new DirCache(pathOf("gitgit.index.aaaa"));
		try {
			dc.read();
			fail("Cache loaded an unsupported extension");
		} catch (CorruptObjectException err) {
			assertEquals("DIRC extension 'aaaa'"
					+ " not supported by this version."
		}
	}

	public void testCorruptChecksumAtFooter() throws Exception {
		final DirCache dc = new DirCache(pathOf("gitgit.index.badchecksum"));
		try {
			dc.read();
			fail("Cache loaded despite corrupt checksum");
		} catch (CorruptObjectException err) {
			assertEquals("DIRC checksum mismatch"
		}
	}

