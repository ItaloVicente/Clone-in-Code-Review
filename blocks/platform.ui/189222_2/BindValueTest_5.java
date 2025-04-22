	@Test
	public void setterShortcut() {
		var target = new WritableValue<String>();
		var model = new AtomicReference<String>(null);

		Bind.oneWay().from(target).toSetter(model::set).bindWithNewContext();

		target.setValue("test");
		assertEquals("test", model.get());
	}

	@Test
	public void setterShortcutWithConverter() {
		var target = new WritableValue<String>();
		var model = new AtomicReference<Integer>(null);

		Bind.oneWay().from(target).convertTo(IConverter.create(Integer::parseInt)).toSetter(model::set)
				.bindWithNewContext();

		target.setValue("77");
		assertEquals(77, (int) model.get());
	}

