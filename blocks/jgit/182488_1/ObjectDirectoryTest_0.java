	@Test
	public void testOpenLooseObjectSuppressStaleFileHandleException()
			throws Exception {
		ObjectId id = ObjectId
				.fromString("873fb8d667d05436d728c52b1d7a09528e6eb59b");
		WindowCursor curs = new WindowCursor(db.getObjectDatabase());

		LooseObjects mock = mock(LooseObjects.class);
		UnpackedObjectCache unpackedObjectCacheMock = mock(
				UnpackedObjectCache.class);

		Mockito.when(mock.getObjectLoader(any()
				.thenThrow(new IOException("Stale File Handle"));
		Mockito.when(mock.open(curs
		Mockito.when(mock.unpackedObjectCache())
				.thenReturn(unpackedObjectCacheMock);

		assertNull(mock.open(curs
		verify(unpackedObjectCacheMock).remove(id);
	}

	@Test
	public void testOpenLooseObjectPropagatesIOExceptions() throws Exception {
		ObjectId id = ObjectId
				.fromString("873fb8d667d05436d728c52b1d7a09528e6eb59b");
		WindowCursor curs = new WindowCursor(db.getObjectDatabase());

		LooseObjects mock = mock(LooseObjects.class);

		Mockito.when(mock.getObjectLoader(any()
				.thenThrow(new IOException("some IO failure"));
		Mockito.when(mock.open(curs

		assertThrows(IOException.class
	}

