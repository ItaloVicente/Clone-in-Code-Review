	@Test
	public void testBoundary() throws IOException {
		assertBoundaryCorrect(AutoCRLFOutputStream.BUFFER_SIZE - 5);
		assertBoundaryCorrect(AutoCRLFOutputStream.BUFFER_SIZE - 4);
		assertBoundaryCorrect(AutoCRLFOutputStream.BUFFER_SIZE - 3);
		assertBoundaryCorrect(AutoCRLFOutputStream.BUFFER_SIZE - 2);
		assertBoundaryCorrect(AutoCRLFOutputStream.BUFFER_SIZE - 1);
		assertBoundaryCorrect(AutoCRLFOutputStream.BUFFER_SIZE);
		assertBoundaryCorrect(AutoCRLFOutputStream.BUFFER_SIZE + 1);
		assertBoundaryCorrect(AutoCRLFOutputStream.BUFFER_SIZE + 2);
		assertBoundaryCorrect(AutoCRLFOutputStream.BUFFER_SIZE + 3);
		assertBoundaryCorrect(AutoCRLFOutputStream.BUFFER_SIZE + 4);
		assertBoundaryCorrect(AutoCRLFOutputStream.BUFFER_SIZE + 5);
	}

	private void assertBoundaryCorrect(int size) throws IOException {
		StringBuilder sb = new StringBuilder(size);
		for (int i = 0; i < size; i++)
			sb.append('a');
		String s = sb.toString();
		assertNoCrLf(s
	}

