		if (ici instanceof CommandContributionItem) {
			CommandContributionItem cmd = (CommandContributionItem) ici;
			assertIcon(cmd, ICONS_ANYTHING_GIF);
		} else if (ici instanceof HandledContributionItem) {
			assertIcon((HandledContributionItem)ici, ICONS_ANYTHING_GIF);
		} else {
			fail("Failed to find correct contribution item: " + ID_DEFAULT + ": " + ici);
		}
		
