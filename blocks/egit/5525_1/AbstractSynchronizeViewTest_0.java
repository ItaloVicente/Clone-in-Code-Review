	@After
	public void closeSynchronizeView() {
		SWTBotView syncView = bot.viewByTitle("Synchronize");
		syncView.close();
	}

