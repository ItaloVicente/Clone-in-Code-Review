	@Test
	public void setterShortcut() {
		var target = new WritableList<String>();
		var model = new AtomicReference<List<String>>(null);

		Bind.oneWay().from(target).toSetter(model::set).bindWithNewContext();

		target.add("test");
		assertEquals(List.of("test"), model.get());
	}

	@Test
	public void setterShortcutWithConverter() {
		var target = new WritableList<String>();
		var model = new AtomicReference<List<Integer>>(null);

		Bind.oneWay().from(target).convertTo(IConverter.create(Integer::parseInt)).toSetter(model::set)
				.bindWithNewContext();

		target.add("77");
		assertEquals(List.of(77), model.get());
	}

