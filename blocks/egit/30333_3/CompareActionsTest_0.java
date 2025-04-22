		if (!noResultLabel.contains("No changes in 'Git (" + PROJ1 + ")'.")) {
			syncViewLabel = bot.viewByTitle("Synchronize").bot().label(2);
			noResultLabel = syncViewLabel.getText();
			assertTrue(noResultLabel.contains("No changes in 'Git (" + PROJ1
					+ ")'."));
		}
