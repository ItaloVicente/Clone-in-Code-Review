	@Test
	public void testBug475357_IconChanges() throws Exception {
		part1.setIconURI(PART_DESC_ICON);
		CTabItem item = ((CTabFolder) partStack.getWidget()).getItem(0);
		Image image = item.getImage();

		part1.setIconURI(PART_ICON);
		assertNotEquals(item.getImage(), image);
	}

	@Test
	public void testBug475357_PartIconOverridesDescriptor() throws Exception {

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

