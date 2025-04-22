
	@Test
	public void testConversion() {
		UpdateListStrategy modelToTarget = new UpdateListStrategy();
		modelToTarget.setConverter(new IConverter() {

			@Override
			public Object getFromType() {
				return String.class;
			}

			@Override
			public Object getToType() {
				return String.class;
			}

			@Override
			public Object convert(Object fromObject) {
				return ((String) fromObject) + "converted";
			}

		});

		dbc.bindList(target, model, new UpdateListStrategy(), modelToTarget);

		model.add("1");
		assertEquals("1converted", target.get(0));

		model.set(0, "2");
		assertEquals("2converted", target.get(0));
	}
