
	@Test
	public void testHeapToByteArrayWithLimit() throws IOException {
		int sz = 2 * Block.SZ;
		try (TemporaryBuffer b = new TemporaryBuffer.Heap(sz / 2
			for (int i = 0; i < sz; i++) {
				b.write('a' + i % 26);
			}
			byte[] prefix = b.toByteArray(5);
			assertEquals(5
			for (int i = 0; i < prefix.length; i++) {
				assertEquals('a' + i % 26
			}
			prefix = b.toByteArray(Block.SZ + 37);
			assertEquals(Block.SZ + 37
			for (int i = 0; i < prefix.length; i++) {
				assertEquals('a' + i % 26
			}
			prefix = b.toByteArray(sz);
			assertEquals(sz
			for (int i = 0; i < prefix.length; i++) {
				assertEquals('a' + i % 26
			}
			prefix = b.toByteArray(sz + 37);
			assertEquals(sz
			for (int i = 0; i < prefix.length; i++) {
				assertEquals('a' + i % 26
			}
		}
	}

	@Test
	public void testFileToByteArrayWithLimit() throws IOException {
		TemporaryBuffer b = new TemporaryBuffer.LocalFile(null
		int sz = 3 * Block.SZ;
		try {
			for (int i = 0; i < sz; i++) {
				b.write('a' + i % 26);
			}
			b.close();
			byte[] prefix = b.toByteArray(5);
			assertEquals(5
			for (int i = 0; i < prefix.length; i++) {
				assertEquals('a' + i % 26
			}
			prefix = b.toByteArray(Block.SZ + 37);
			assertEquals(Block.SZ + 37
			for (int i = 0; i < prefix.length; i++) {
				assertEquals('a' + i % 26
			}
			prefix = b.toByteArray(sz);
			assertEquals(sz
			for (int i = 0; i < prefix.length; i++) {
				assertEquals('a' + i % 26
			}
			prefix = b.toByteArray(sz + 37);
			assertEquals(sz
			for (int i = 0; i < prefix.length; i++) {
				assertEquals('a' + i % 26
			}
		} finally {
			b.destroy();
		}
	}
