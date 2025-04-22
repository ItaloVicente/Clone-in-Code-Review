		bot.text().setText("these are not the features you're looking for");
		bot.sleep(300); // wait for filter to hit
		assertFalse(bot.tree().hasItems());
		bot.text().selectAll();
		bot.text().typeText(featureName);
		bot.tree().select(featureName);
