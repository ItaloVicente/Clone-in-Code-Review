	@Test
	public void testBug475357() throws Exception {
		String oneIcon = "platform:/plugin/org.eclipse.e4.ui.tests/icons/pinned_ovr.png";
		part.getTransientData().put(SWTPartRenderer.ICON_URI_FOR_PART, oneIcon);

		part.setIconURI(oneIcon);
		CTabItem item = ((CTabFolder) partStack.getWidget()).getItem(0);
		assertEquals(8, item.getImage().getImageData().width);

		String otherIcon = "platform:/plugin/org.eclipse.e4.ui.tests/icons/filenav_nav.png";
		part.setIconURI(otherIcon);

		assertEquals(otherIcon, part.getTransientData().get(SWTPartRenderer.ICON_URI_FOR_PART));
		assertEquals(16, item.getImage().getImageData().width);
	}

