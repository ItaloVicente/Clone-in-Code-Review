
	@Test
	public void testBug475357_IconChanges() throws Exception {
		MPartDescriptor partDescriptor = ems.createModelElement(MPartDescriptor.class);
		partDescriptor.setElementId("myelementid");
		partDescriptor.setLabel("some title");
		partDescriptor.setIconURI(PART_DESC_ICON);
		application.getDescriptors().add(partDescriptor);

		MPart part1 = ems.createPart(partDescriptor);
		MPart part2 = ems.createPart(partDescriptor);

		partStack.getChildren().add(part1);
		partStack.getChildren().add(part2);

		contextRule.createAndRunWorkbench(window);

		part1.setIconURI(PART_DESC_ICON);
		CTabItem item = ((CTabFolder) partStack.getWidget()).getItem(0);
		Image image = item.getImage();

		part1.setIconURI(PART_ICON);
		assertNotEquals(item.getImage(), image);
	}

	@Test
	public void testBug475357_PartIconOverridesDescriptor() throws Exception {
		MPartDescriptor partDescriptor = ems.createModelElement(MPartDescriptor.class);
		partDescriptor.setElementId("myelementid");
		partDescriptor.setLabel("some title");
		partDescriptor.setIconURI(PART_DESC_ICON);
		application.getDescriptors().add(partDescriptor);

		MPart part1 = ems.createPart(partDescriptor);
		MPart part2 = ems.createPart(partDescriptor);

		partStack.getChildren().add(part1);
		partStack.getChildren().add(part2);

		contextRule.createAndRunWorkbench(window);

		CTabItem item = ((CTabFolder) partStack.getWidget()).getItem(1);
		Image descImage = item.getImage();

		part2.setIconURI(PART_ICON);
		Image partIcon = item.getImage();
		assertNotEquals(partIcon, descImage);

		part2.setIconURI(null);
		Image ovrwriteIcon = item.getImage();
		assertNotEquals(ovrwriteIcon, partIcon);
		assertEquals(ovrwriteIcon, descImage);
	}

