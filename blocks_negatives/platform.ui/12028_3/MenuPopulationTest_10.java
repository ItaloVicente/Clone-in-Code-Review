		assertTrue(ici instanceof CommandContributionItem);
		CommandContributionItem cmd = (CommandContributionItem) ici;

		ImageDescriptor icon = (ImageDescriptor) iconField.get(cmd);
		assertNotNull(icon);
		String iconString = icon.toString();
		assertEquals(ICONS_ANYTHING_GIF, iconString.substring(iconString
				.lastIndexOf('/')));

