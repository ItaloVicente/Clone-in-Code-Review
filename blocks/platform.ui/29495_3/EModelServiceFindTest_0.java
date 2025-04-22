	@Test
	public void testFindAddons() {
		MApplication application = createApplication();
		EModelService modelService = (EModelService) application.getContext()
				.get(EModelService.class.getName());
		assertNotNull(modelService);

		MAddon addon = MApplicationFactory.INSTANCE.createAddon();

		application.getAddons().add(addon);

		List<MAddon> elements = modelService.findElements(application,
				MAddon.class, EModelService.ANYWHERE, new Selector() {
					@Override
					public boolean select(MApplicationElement element) {
						return (element instanceof MAddon);
					}
				});

		assertEquals(1, elements.size());
		assertEquals(addon, elements.get(0));
	}

