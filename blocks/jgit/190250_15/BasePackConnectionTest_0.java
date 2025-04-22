	@Test
	public void testReadAdvertisedRefsShouldThrowExceptionWithOriginalCause() {
		try (FailingBasePackConnection basePackConnection =
				new FailingBasePackConnection()) {
			Exception result = assertThrows(NoRemoteRepositoryException.class
					basePackConnection::readAdvertisedRefs);
			assertEquals(EOFException.class
		}
	}

