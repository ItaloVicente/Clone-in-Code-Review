	@Test
	public void testReadAdvertisedRefsShouldThrowExceptionWithOriginalCause()
			throws URISyntaxException {
		URIish uri = new URIish("uri");
		EOFException originalException = new EOFException("Original exception");
		EOFException exceptionToSuppress =
				new EOFException("Cause to suppress");
		NoRemoteRepositoryException noRepo =
				new NoRemoteRepositoryException(uri

		try(FailingBasePackConnection basePackConnection =
				new FailingBasePackConnection(uri
						exceptionToSuppress
		) {
			Exception exception = assertThrows(
					NoRemoteRepositoryException.class
					basePackConnection::callReadAdvertisedRefs);
			assertThat(exception.getCause()
			assertEquals(1
			assertThat(exception.getSuppressed()[0]
					equalTo(exceptionToSuppress));
		}
	}

