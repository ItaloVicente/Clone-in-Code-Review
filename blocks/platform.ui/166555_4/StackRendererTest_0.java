	@Test
	public void testTabStateHandlerWhenOneOfSupportedTagChangeEvents() throws Exception {
		MPart part = ems.createModelElement(MPart.class);
		partStack.getChildren().add(part);
		part.setLabel("some title");

		CTabItemStylingMethodsListener executedMethodsListener = new CTabItemStylingMethodsListener(part);
