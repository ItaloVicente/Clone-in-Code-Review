		assertEquals(0, handler.getUnusedResources().size());
	}

	public void testDisposeHandler() throws Exception {
		ThemeDefinitionChangedHandlerTestable handler = spy(new ThemeDefinitionChangedHandlerTestable());

		Resource resource1 = mock(Resource.class);
		doReturn(false).when(resource1).isDisposed();
		handler.getUnusedResources().add(resource1);

		Resource resource2 = mock(Resource.class);
		doReturn(true).when(resource2).isDisposed();
		handler.getUnusedResources().add(resource2);

		handler.dispose();

		assertTrue(handler.getUnusedResources().isEmpty());

		verify(resource1, times(1)).isDisposed();
		verify(resource1, times(1)).dispose();

		verify(resource2, times(1)).isDisposed();
		verify(resource2, never()).dispose();
