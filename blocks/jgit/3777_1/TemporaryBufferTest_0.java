	@Test
	public void testInCoreInputStream() throws IOException {
		final int cnt = 256;
		final byte[] test = new TestRng(getName()).nextBytes(cnt);
		final TemporaryBuffer.Heap b = new TemporaryBuffer.Heap(cnt + 4);
		b.write(test);
		b.close();

		InputStream in = b.openInputStream();
		byte[] act = new byte[cnt];
		IO.readFully(in
		assertTrue(Arrays.equals(test
	}

