	@Test
	public void testReadWriteV4() throws Exception {
		final File file = pathOf("gitgit.index.v4");
		final DirCache dc = new DirCache(file
		dc.read();
		assertEquals(DirCacheVersion.DIRC_VERSION_PATHCOMPRESS
				dc.getVersion());
		assertEquals(5
		assertV4TreeEntry(0
		assertV4TreeEntry(1
				dc);
		assertV4TreeEntry(2
		assertV4TreeEntry(3
		assertV4TreeEntry(4

		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		dc.writeTo(null
		final byte[] indexBytes = bos.toByteArray();
		final byte[] expectedBytes = IO.readFully(file);
		assertArrayEquals(expectedBytes
	}

