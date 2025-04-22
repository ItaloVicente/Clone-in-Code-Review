	@Test
	public void testEmptyTar() throws Exception {
				"git archive --format=tar " + emptyTree
		assertArrayEquals(new String[0]
	}

