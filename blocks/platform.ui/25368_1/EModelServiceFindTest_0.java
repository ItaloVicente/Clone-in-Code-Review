	public void testFind_ViewMenus() {
		MApplication application = createApplication();
		EModelService modelService = (EModelService) application.getContext()
				.get(EModelService.class.getName());
		assertNotNull(modelService);
		List<? extends MApplicationElement> elements;
		elements = modelService.findElements(application, MPopupMenu.class,
				EModelService.IN_PART, getSelector(MPopupMenu.class));
		assertEquals(1, elements.size());

	}

