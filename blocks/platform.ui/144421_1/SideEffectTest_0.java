	@Test
	public void testObservableInMap() {
		HashMap<String, IObservableValue<String>> hashMap = new HashMap<>();
		WritableValue<String> writableValue = new WritableValue<>();
		writableValue.setValue("Simon");
		hashMap.put("Simon", writableValue);

		ISideEffect.create(() -> {
			IObservableValue<String> observableValue = hashMap.get("Simon");
			System.out.println(observableValue.getValue());
			sideEffectInvocations++;
		});

		assertEquals(1, sideEffectInvocations);

		writableValue.setValue("Stefan");
		runAsync();

		assertEquals(2, sideEffectInvocations);
	}

