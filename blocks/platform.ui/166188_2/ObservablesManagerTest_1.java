	@Test
	public void testContextIsDisposed() {
		IObservableValue<?> targetOv = new WritableValue<>();
		IObservableValue<?> modelOv = new WritableValue<>();

		dbc.bindValue(targetOv, modelOv);

		ValidationStatusProvider dummyProvider = createDummyBinding(Observables.emptyObservableList(),
				Observables.emptyObservableList());

		dbc.addValidationStatusProvider(dummyProvider);

		ObservablesManager observablesManager = new ObservablesManager();

		observablesManager.addContext(dbc, true, false);

		observablesManager.dispose();

		assertTrue(dummyProvider.isDisposed());
		assertTrue(targetOv.isDisposed());
		assertFalse(modelOv.isDisposed());
	}

	@Test
	public void testContextIsNotDisposed() {
		IObservableValue<?> targetOv = new WritableValue<>();
		IObservableValue<?> modelOv = new WritableValue<>();

		dbc.bindValue(targetOv, modelOv);

		ValidationStatusProvider dummyProvider = createDummyBinding(Observables.emptyObservableList(),
				Observables.emptyObservableList());

		dbc.addValidationStatusProvider(dummyProvider);

		ObservablesManager observablesManager = new ObservablesManager();

		observablesManager.addObservablesFromContext(dbc, true, true);

		observablesManager.dispose();

		assertFalse(dummyProvider.isDisposed());
	}

