		String expected = "No changes in 'Git (" + PROJ1 + ")'.";
		if (!noResultLabel.contains(expected)) {
			syncViewLabel = bot.viewByTitle("Synchronize").bot().label(2);
			noResultLabel = syncViewLabel.getText();
			assertTrue(noResultLabel.contains(expected));
		}
