
	@Test
	public void testConversion() {
		UpdateListStrategy modelToTarget = new UpdateListStrategy(true, true, POLICY_UPDATE);
		dbc.bindList(target, model, new UpdateListStrategy(), modelToTarget);

		modelToTarget.setConverter(
				IConverter.create(String.class, String.class, fromObject -> ((String) fromObject) + "converted"));

		model.add("1");
		assertEquals("1converted", target.get(0));

		model.set(0, "2");
		assertEquals("2converted", target.get(0));
	}

	@Test
	public void testLegacyConversion() {
		UpdateListStrategy modelToTarget = new UpdateListStrategy();
		dbc.bindList(target, model, new UpdateListStrategy(), modelToTarget);

		modelToTarget.setConverter(
				IConverter.create(String.class, String.class, fromObject -> ((String) fromObject) + "converted"));

		model.add("1");
		assertEquals("1converted", target.get(0));

		model.set(0, "2");
		assertEquals("2", target.get(0));
	}
