
	private void typeInto(String textLabel, String textInput) {
		SWTBotText developText = bot.textWithLabel(textLabel);
		developText.selectAll();
		developText.typeText(textInput);
	}
