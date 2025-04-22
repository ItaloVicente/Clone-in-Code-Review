	public void testFindMKeyBindings() {
		MApplication application = createApplication();
		EModelService modelService = (EModelService) application.getContext()
				.get(EModelService.class.getName());
		assertNotNull(modelService);

		MBindingTable bindingTable = MCommandsFactory.INSTANCE
				.createBindingTable();
		MKeyBinding keyBinding = MCommandsFactory.INSTANCE.createKeyBinding();
		bindingTable.getBindings().add(keyBinding);

		application.getBindingTables().add(bindingTable);

		List<MKeyBinding> elements = modelService.findElements(application,
				MKeyBinding.class, EModelService.ANYWHERE, new Selector() {
					@Override
					public boolean select(MApplicationElement element) {
						return (element instanceof MKeyBinding);
					}
				});

		assertEquals(1, elements.size());
		assertEquals(keyBinding, elements.get(0));
	}

