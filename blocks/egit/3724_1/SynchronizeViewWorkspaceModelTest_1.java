		SWTBot viewBot = bot.viewByTitle("Synchronize").bot();
		@SuppressWarnings("unchecked")
		Matcher matcher = allOf(widgetOfType(Label.class),
				withRegex("No changes in .*"));

		@SuppressWarnings("unchecked")
		SWTBotLabel l = new SWTBotLabel((Label) viewBot.widget(matcher));
		assertNotNull(l);
