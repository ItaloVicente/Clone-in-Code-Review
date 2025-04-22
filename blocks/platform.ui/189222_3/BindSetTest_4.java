	@Test
	public void setterShortcut() {
		var target = new WritableSet<String>();
		var model = new AtomicReference<Set<String>>(null);

		Bind.oneWay().from(target).toSetter(model::set).bindWithNewContext();

		target.add("test");
		assertEquals(Set.of("test"), model.get());
	}

	@Test
	public void setterShortcutWithConverter() {
		var target = new WritableSet<String>();
		var model = new AtomicReference<Set<Integer>>(null);

		Bind.oneWay().from(target).convertTo(IConverter.create(Integer::parseInt)).toSetter(model::set)
				.bindWithNewContext();

		target.add("77");
		assertEquals(Set.of(77), model.get());
	}

