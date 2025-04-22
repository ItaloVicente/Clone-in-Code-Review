
	public void testFindPlaceholder() {
		MApplication application = createApplication();

		MPartStack stack = modelService.findElements(application, null,
				MPartStack.class, null).get(0);

		MPlaceholder ph = modelService.createModelElement(MPlaceholder.class);
		ph.setRef(BasicFactoryImpl.eINSTANCE.createPart());
		stack.getChildren().add(ph);

		List<MPlaceholder> elements = modelService.findElements(application,
				null, MPlaceholder.class, null);
		assertEquals(1, elements.size());
	}
