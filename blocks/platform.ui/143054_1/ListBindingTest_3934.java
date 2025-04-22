
	@Test
	public void testConversion() {
		UpdateListStrategy<String, String> modelToTarget = new UpdateListStrategy<>();
		modelToTarget.setConverter(new IConverter<String, String>() {
			@Override
			public Object getFromType() {
				return String.class;
			}

			@Override
			public Object getToType() {
				return String.class;
			}
			@Override
			public String convert(String fromObject) {
				return fromObject + "converted";
			}

		});

		dbc.bindList(target, model, new UpdateListStrategy<>(), modelToTarget);

		model.add("1");
		assertEquals("1converted", target.get(0));

		model.set(0, "2");
		assertEquals("2converted", target.get(0));
	}
