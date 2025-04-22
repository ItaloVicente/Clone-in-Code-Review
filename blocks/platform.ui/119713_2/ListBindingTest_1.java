
	@Test
	public void testConversion() {
		UpdateListStrategy2 modelToTarget = new UpdateListStrategy2();
		modelToTarget.setConverter(
				IConverter.create(String.class, String.class, fromObject -> ((String) fromObject) + "converted"));
		dbc.bindList(target, model, new UpdateListStrategy2(), modelToTarget);

		model.add("1");
		assertEquals("1converted", target.get(0));

		model.set(0, "2");
		assertEquals("2converted", target.get(0));
	}

	@Test
	public void testLegacyConversion() {
		UpdateListStrategy modelToTarget = new UpdateListStrategy();
		modelToTarget.setConverter(
				IConverter.create(String.class, String.class, fromObject -> ((String) fromObject) + "converted"));

		dbc.bindList(target, model, new UpdateListStrategy(), modelToTarget);


		model.add("1");
		assertEquals("1converted", target.get(0));

		model.set(0, "2");
		assertEquals("2", target.get(0));
	}
