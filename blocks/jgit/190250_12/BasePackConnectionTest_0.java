	@Test
	public void testReadAdvertisedRefsShouldThrowExceptionWithOriginalCause() {
		EOFException eofException = new EOFException("Original exception");

		try(FailingBasePackConnection basePackConnection =
				new FailingBasePackConnection(eofException)
		) {
			Exception result = assertThrows(
					NoRemoteRepositoryException.class
					basePackConnection::readAdvertisedRefs);
			assertEquals(eofException
		}
	}

