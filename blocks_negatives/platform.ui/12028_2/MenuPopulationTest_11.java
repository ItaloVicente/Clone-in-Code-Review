		assertTrue(ici instanceof CommandContributionItem);
		cmd = (CommandContributionItem) ici;
		icon = (ImageDescriptor) iconField.get(cmd);
		assertNotNull(icon);
		iconString = icon.toString();
		assertEquals(ICONS_VIEW_GIF, iconString.substring(iconString
				.lastIndexOf('/')));
