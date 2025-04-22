
	@Test
	public void testDisposeMultipleTargets_Bug546983() {
		DataBindingContext context = new DataBindingContext();

		IObservableList<IObservable> targets = new WritableList<>(
				Arrays.asList(new WritableValue<>(), new WritableValue<>()), WritableValue.class);
		IObservableList<IObservable> models = new WritableList<>(
				Arrays.asList(new WritableValue<>(), new WritableValue<>()), WritableValue.class);

		context.addBinding(createDummyBinding(targets, models));
		ObservablesManager manager = new ObservablesManager();
		manager.addObservablesFromContext(context, true, true);
		manager.dispose();

		assertTrue(targets.get(0).isDisposed());
		assertTrue(targets.get(1).isDisposed());
		assertTrue(models.get(0).isDisposed());
		assertTrue(models.get(1).isDisposed());
	}

	private Binding createDummyBinding(IObservableList<IObservable> targets, IObservableList<IObservable> models) {
		return new Binding(new WritableValue<>(), new WritableValue<>()) {
			@Override
			public IObservableList<IObservable> getTargets() {
				return targets;
			}
			@Override
			public IObservableList<IObservable> getModels() {
				return models;
			}

			@Override
			public IObservableValue<IStatus> getValidationStatus() {
				return Observables.constantObservableValue(Status.OK_STATUS);
			}
			@Override
			public void validateTargetToModel() {
			}
			@Override
			public void validateModelToTarget() {
			}
			@Override
			public void updateTargetToModel() {
			}
			@Override
			public void updateModelToTarget() {
			}
			@Override
			protected void preInit() {
			}
			@Override
			protected void postInit() {
			}
		};
	}
