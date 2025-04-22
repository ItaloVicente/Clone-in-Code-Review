	public void testHeap() throws IOException {
		final TemporaryBuffer b = new TemporaryBuffer.Heap(2 * 8 * 1024);
		final byte[] r = new byte[8 * 1024];
		b.write(r);
		b.write(r);
		try {
			b.write(1);
			fail("accepted too many bytes of data");
		} catch (IOException e) {
			assertEquals("In-memory buffer limit exceeded"
		}
	}
