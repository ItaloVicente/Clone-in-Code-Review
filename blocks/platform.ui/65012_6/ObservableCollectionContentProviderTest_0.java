
	public void testKnownElementsAreFilledOnSettingAFilledCollectionAsInput() {
		final IObservableList input = new WritableList(Collections.singletonList("element"), null);
		contentProvider = new ObservableListContentProvider();
		contentProvider.inputChanged(viewer, null, input);

		IObservableSet knownElements = contentProvider.getKnownElements();
		assertEquals(1, knownElements.size());
		assertTrue(knownElements.containsAll(input));
	}
