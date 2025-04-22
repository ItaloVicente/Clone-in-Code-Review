	@Test
	public void testCreateValidatorWithNullConverter() {
		UpdateValueStrategyStub<Object, Object> strategy = new UpdateValueStrategyStub<>();

		strategy.fillDefaults(WritableValue.withValueType(String.class), WritableValue.withValueType(Object.class));
		strategy.setConverter(null);
		strategy.createValidator(String.class, Object.class);
	}

