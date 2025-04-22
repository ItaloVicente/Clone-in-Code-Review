		assertNoCrLf("\0\n"
	}

	@Test
	public void testBoundary() throws IOException {
		for (int i = AutoCRLFInputStream.BUFFER_SIZE - 10; i < AutoCRLFInputStream.BUFFER_SIZE + 10; i++) {
			String s1 = AutoCRLFOutputStreamTest.repeat("a"
			assertNoCrLf(s1
			String s2 = AutoCRLFOutputStreamTest.repeat("\0"
			assertNoCrLf(s2
		}
