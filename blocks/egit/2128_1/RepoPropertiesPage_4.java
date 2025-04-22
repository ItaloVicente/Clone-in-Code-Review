	public void setPushDestination(String destination) {
		SWTBotCombo destinationCombo = bot.comboBox();
		String[] items = destinationCombo.items();
		for (int i = 0; i < items.length; i++) {
			if (items[i].startsWith(destination)) {
				destinationCombo.setSelection(i);
				return;
			}
		}
		fail("ould not find destination " + destination);
	}

