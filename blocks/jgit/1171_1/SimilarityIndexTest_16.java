	public void testIndexingLargeObject() throws IOException {
				+ "B\n").getBytes("UTF-8");
		SimilarityIndex si = new SimilarityIndex();
		si.hash(new ByteArrayInputStream(in)
		assertEquals(2
	}

