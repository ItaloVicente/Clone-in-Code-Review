	public void testReadWriteV3() throws Exception {
		final File file = pathOf("gitgit.index.v3");
		final DirCache dc = new DirCache(file
				FS.DETECTED);
		dc.read();

		assertEquals(10
		assertV3TreeEntry(0
		assertV3TreeEntry(1
		assertV3TreeEntry(2
		assertV3TreeEntry(3
		assertV3TreeEntry(4
		assertV3TreeEntry(5
		assertV3TreeEntry(6
		assertV3TreeEntry(7
		assertV3TreeEntry(8
		assertV3TreeEntry(9

		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		dc.writeTo(bos);
		final byte[] indexBytes = bos.toByteArray();

		final byte[] expectedBytes = IO.readFully(file);
		for (int i = 0; i < expectedBytes.length; i++) {
			assertEquals(expectedBytes[i]
		}
	}

	private void assertV3TreeEntry(int index
		final DirCacheEntry entry = dc.getEntry(index);
		assertEquals(path
		assertEquals(skipWorkTree
		assertEquals(intentToAdd
	}

