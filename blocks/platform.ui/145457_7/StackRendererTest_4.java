	@Test
	public void testBug475357_IconChanges() throws Exception {
		part1.getTransientData().put(SWTPartRenderer.ICON_URI_FOR_PART, PART_DESC_ICON);

		part1.setIconURI(PART_DESC_ICON);
		CTabItem item = ((CTabFolder) partStack.getWidget()).getItem(0);
		assertEquals(PART_DESC_ICON, part1.getTransientData().get(SWTPartRenderer.ICON_URI_FOR_PART));
		assertEquals(8, item.getImage().getImageData().width);

		part1.setIconURI(PART_ICON);
		assertEquals(PART_ICON, part1.getTransientData().get(SWTPartRenderer.ICON_URI_FOR_PART));
		assertEquals(16, item.getImage().getImageData().width);
	}

	@Test
	public void testBug475357_PartIconOverridesDescriptor() throws Exception {

		part1.setIconURI(PART_ICON);
		assertEquals(PART_ICON, part1.getTransientData().get(SWTPartRenderer.ICON_URI_FOR_PART));
		assertEquals(PART_DESC_ICON, part2.getTransientData().get(SWTPartRenderer.ICON_URI_FOR_PART));

		part1.setIconURI(null);
		assertEquals(PART_DESC_ICON, part1.getTransientData().get(SWTPartRenderer.ICON_URI_FOR_PART));
	}

