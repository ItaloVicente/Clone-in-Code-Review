
	@Test
	public void testIsStaleFileHandleWithDirectCause() throws Exception {
		assertTrue(FileUtils.isStaleFileHandle(IO_EXCEPTION));
	}

	@Test
	public void testIsStaleFileHandleWithIndirectCause() throws Exception {
		assertFalse(
				FileUtils.isStaleFileHandle(IO_EXCEPTION_WITH_CAUSE));
	}

	@Test
	public void testIsStaleFileHandleInCausalChainWithDirectCause()
			throws Exception {
		assertTrue(
				FileUtils.isStaleFileHandleInCausalChain(IO_EXCEPTION));
	}

	@Test
	public void testIsStaleFileHandleInCausalChainWithIndirectCause()
			throws Exception {
		assertTrue(FileUtils
				.isStaleFileHandleInCausalChain(IO_EXCEPTION_WITH_CAUSE));
	}
