
	@Test
	public void testHeapWithEstimatedSize() throws IOException {
		int sz = 2 * Block.SZ;
		try (TemporaryBuffer b = new TemporaryBuffer.Heap(sz
			for (int i = 0; i < sz; i++) {
				b.write('x');
			}
			try {
				b.write(1);
				fail("accepted too many bytes of data");
			} catch (IOException e) {
				assertEquals("In-memory buffer limit exceeded"
			}

			try (InputStream in = b.openInputStream()) {
				for (int i = 0; i < sz; i++) {
					assertEquals('x'
				}
				assertEquals(-1
			}
		}
	}
