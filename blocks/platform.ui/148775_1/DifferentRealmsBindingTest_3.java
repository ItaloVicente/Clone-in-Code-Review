		targetRealm.waitUntilBlocking();
		targetRealm.processQueue();
		targetRealm.unblock();
		assertTrue(errorStatusses.toString(), errorStatusses.isEmpty());
	}

	@Test
	public void testSetBindingUpdatesDontInterferWithObservableDisposing() throws Exception {
		final IObservableSet<String> model = new WritableSet<>(modelRealm);
		final IObservableSet<String> target = new WritableSet<>(targetRealm);
		dbc.bindSet(target, model);

		waitForBindingInitiated();

		modelRealm.exec(() -> model.add("one"));

		testDisposeRaceCondition(target);
	}

	@Test
	public void testListBindingUpdatesDontInterferWithObservableDisposing() throws Exception {
		final IObservableList<String> model = new WritableList<>(modelRealm);
		final IObservableList<String> target = new WritableList<>(targetRealm);
		dbc.bindList(target, model);

		waitForBindingInitiated();

		modelRealm.exec(() -> model.add("one"));

		testDisposeRaceCondition(target);
	}

	@Test
	public void testValueBindingUpdatesDontInterferWithObservableDisposing() throws Exception {
		final IObservableValue<String> model = new WritableValue<>(modelRealm);
		final IObservableValue<String> target = new WritableValue<>(targetRealm);
		dbc.bindValue(target, model);

		waitForBindingInitiated();

		modelRealm.exec(() -> model.setValue("one"));

		testDisposeRaceCondition(target);
	}

	private void testDisposeRaceCondition(final IObservable target) {
		modelRealm.processQueue();
		targetRealm.waitUntilBlocking();

		target.dispose();

		modelRealm.unblock();
		targetRealm.processQueue();
		targetRealm.unblock();
		validationRealm.unblock();

