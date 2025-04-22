	@Test
	public void testReadAdvertisedShouldHandleExceptionCorrectly() throws URISyntaxException {
		URIish uri = new URIish("uri");
		EOFException exceptionToSuppress = new EOFException("Cause to suppress");
		EOFException originalException = new EOFException("Original exception");
		NoRemoteRepositoryException noRepo = new NoRemoteRepositoryException(uri

		FailingBasePackConnection basePackConnection = new FailingBasePackConnection(uri

		Exception exception = assertThrows(NoRemoteRepositoryException.class
		assertThat(exception.getCause()
		assertEquals(1
		assertThat(exception.getSuppressed()[0]
	}

