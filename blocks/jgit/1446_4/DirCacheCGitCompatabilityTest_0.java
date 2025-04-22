	public void testReadWriteV3() throws Exception {
		final File file = pathOf("gitgit.index.v3.skipWorkTree");
		final DirCache dc = new DirCache(file
		dc.read();

		assertEquals(7
		assertV3TreeEntry(0
		assertV3TreeEntry(1
		assertV3TreeEntry(2
		assertV3TreeEntry(3
		assertV3TreeEntry(4
		assertV3TreeEntry(5
		assertV3TreeEntry(6

		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		dc.writeTo(bos);
		final byte[] indexBytes = bos.toByteArray();
		final byte[] expectedBytes = IO.readFully(file);
		assertTrue(Arrays.equals(expectedBytes
	}

	private static void assertV3TreeEntry(int indexPosition
			boolean skipWorkTree
		final DirCacheEntry entry = dc.getEntry(indexPosition);
		assertEquals(path
		assertEquals(skipWorkTree
		assertEquals(intentToAdd
	}

