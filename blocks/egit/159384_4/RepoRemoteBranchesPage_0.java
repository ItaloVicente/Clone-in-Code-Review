	public void selectTagOption(TagOpt option) {
		int index = 0;
		switch (option) {
		case AUTO_FOLLOW:
			break;
		case FETCH_TAGS:
			index = 1;
			break;
		case NO_TAGS:
			index = 2;
			break;
		default:
			Assert.fail("unsupported tag option");
			break;
		}
		bot.radio(index).click();
	}

