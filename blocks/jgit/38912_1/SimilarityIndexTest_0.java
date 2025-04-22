	@Test
	public void testCommonScore_SameFiles_CR_canonicalization()
			throws TableFullException {
				+ "B\r\n";
		SimilarityIndex src = hash(text);
		SimilarityIndex dst = hash(text.replace("\r"
		assertEquals(8
		assertEquals(8

		assertEquals(100
		assertEquals(100
	}

	@Test
	public void testCommonScoreLargeObject_SameFiles_CR_canonicalization()
			throws TableFullException
				+ "B\r\n";
		SimilarityIndex src = new SimilarityIndex();
		byte[] bytes1 = text.getBytes("UTF-8");
		src.hash(new ByteArrayInputStream(bytes1)
		src.sort();

		SimilarityIndex dst = new SimilarityIndex();
		byte[] bytes2 = text.replace("\r"
		dst.hash(new ByteArrayInputStream(bytes2)
		dst.sort();

		assertEquals(8
		assertEquals(8

		assertEquals(100
		assertEquals(100
	}

