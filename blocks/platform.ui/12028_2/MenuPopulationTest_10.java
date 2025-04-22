		if (ici instanceof CommandContributionItem) {
			assertIcon((CommandContributionItem)ici, ICONS_BINARY_GIF);
		} else if (ici instanceof HandledContributionItem) {
			assertIcon((HandledContributionItem)ici, ICONS_BINARY_GIF);
		} else {
			fail("Failed to find correct contribution item: " + ID_ALL + ": " + ici);
		}
		
