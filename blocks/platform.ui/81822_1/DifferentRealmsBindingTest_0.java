
	public void testBindComputedListToWritableListInDifferentRealm() {
		final IObservableValue modelValue = new WritableValue(validationRealm);
		final IObservableList model = new ComputedList(validationRealm) {
			@Override
			protected List calculate() {
				return Collections.singletonList(modelValue.getValue());
			}
		};
		final ObservableList target = new WritableList(targetAndModelRealm);

		dbc.bindList(target, model);
		modelValue.setValue("Test");
		targetAndModelRealm.waitUntilBlocking();
		targetAndModelRealm.processQueue();
		targetAndModelRealm.unblock();
		assertTrue(errorStatusses.toString(), errorStatusses.isEmpty());
	}
