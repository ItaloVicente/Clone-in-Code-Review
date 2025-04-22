	@Test
	public void testOpenLooseObjectSuppressStaleFileHandleException() throws Exception {
		ObjectId id = ObjectId.fromString("873fb8d667d05436d728c52b1d7a09528e6eb59b");
		WindowCursor curs = new WindowCursor(db.getObjectDatabase());

		ObjectDirectory mock = Mockito.mock(ObjectDirectory.class);
		UnpackedObjectCache unpackedObjectCacheMock = Mockito.mock(UnpackedObjectCache.class);

		Mockito.when(mock.getObjectLoader(any()
		Mockito.when(mock.openLooseObject(curs
		Mockito.when(mock.unpackedObjectCache()).thenReturn(unpackedObjectCacheMock);

		assertNull(mock.openLooseObject(curs
		verify(unpackedObjectCacheMock).remove(id);
	}

	@Test
	public void testOpenLooseObjectPropagatesIOExceptions() throws Exception {
		ObjectId id = ObjectId.fromString("873fb8d667d05436d728c52b1d7a09528e6eb59b");
		WindowCursor curs = new WindowCursor(db.getObjectDatabase());

		ObjectDirectory mock = Mockito.mock(ObjectDirectory.class);

		Mockito.when(mock.getObjectLoader(any()
		Mockito.when(mock.openLooseObject(curs

		assertThrows(IOException.class
	}

