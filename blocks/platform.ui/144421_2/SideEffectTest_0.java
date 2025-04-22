	@Test
	public void testObservableInMap() {
		HashMap<String, IObservableValue<String>> hashMap = new HashMap<>();
		IObservableValue<String> observableValue = new WritableValue<>();
		observableValue.setValue("Simon");
		hashMap.put("Simon", observableValue);

		ISideEffect.create(() -> {
			IObservableValue<String> observable = hashMap.get("Simon");
			observable.getValue();
			sideEffectInvocations++;
		});

		assertEquals(1, sideEffectInvocations);

		observableValue.setValue("Stefan");
		runAsync();

		assertEquals(2, sideEffectInvocations);
	}

