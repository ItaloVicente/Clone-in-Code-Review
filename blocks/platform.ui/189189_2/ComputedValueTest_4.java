	@Test
	public void testCreate() throws Exception {
		WritableValue<Integer> value = new WritableValue<>(42, null);
		IObservableValue<Integer> cv = ComputedValue.create(value::getValue);
		assertEquals(value.getValue(), cv.getValue());
		value.setValue(44);
		assertEquals(value.getValue(), cv.getValue());
	}

