	private SWTBotText user() {
		return bot.textWithLabel("User:");
	}

	private SWTBotText password() {
		return bot.textWithLabel("Password:");
	}

	private SWTBotCheckBox storeCheckBox() {
		return bot.checkBoxWithLabel("Store in Secure Store");
	}
		
