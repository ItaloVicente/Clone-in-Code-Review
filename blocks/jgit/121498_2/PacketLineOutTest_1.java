	@Test
	public void testWriteDelim() throws IOException {
		out.writeDelim();
		assertBuffer("0001");
	}

