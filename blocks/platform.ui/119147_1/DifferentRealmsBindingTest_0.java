
	@Test
	public void testBindComputedListToWritableListInDifferentRealm() {
		final IObservableValue<String> modelValue = new WritableValue<>(validationRealm);
		final IObservableList<String> model = new ComputedList<String>(validationRealm) {
			@Override
			protected List<String> calculate() {
				return Collections.singletonList(modelValue.getValue());
			}
		};
		final IObservableList<String> target = new WritableList<>(targetAndModelRealm);

		dbc.bindList(target, model);
		modelValue.setValue("Test");
		targetAndModelRealm.waitUntilBlocking();
		targetAndModelRealm.processQueue();
		targetAndModelRealm.unblock();
		assertTrue(errorStatusses.toString(), errorStatusses.isEmpty());
	}

	@Test
	public void testBindComputedSetToWritableSetInDifferentRealm() {
		final IObservableValue<String> modelValue = new WritableValue<>(validationRealm);
		final IObservableSet<String> model = new ComputedSet<String>(validationRealm) {
			@Override
			protected Set<String> calculate() {
				return Collections.singleton(modelValue.getValue());
			}
		};
		final IObservableSet<String> target = new WritableSet<>(targetAndModelRealm);

		dbc.bindSet(target, model);
		modelValue.setValue("Test");
		targetAndModelRealm.waitUntilBlocking();
		targetAndModelRealm.processQueue();
		targetAndModelRealm.unblock();
		assertTrue(errorStatusses.toString(), errorStatusses.isEmpty());
	}

	@Test
	public void testBindComputedValueToWritableValueInDifferentRealm() {
		final IObservableValue<String> modelValue = new WritableValue<>(validationRealm);
		final IObservableValue<String> model = new ComputedValue<String>(validationRealm) {
			@Override
			protected String calculate() {
				return modelValue.getValue();
			}
		};
		final IObservableValue<String> target = new WritableValue<>(targetAndModelRealm);

		dbc.bindValue(target, model);
		modelValue.setValue("Test");
		targetAndModelRealm.waitUntilBlocking();
		targetAndModelRealm.processQueue();
		targetAndModelRealm.unblock();
		assertTrue(errorStatusses.toString(), errorStatusses.isEmpty());
	}
