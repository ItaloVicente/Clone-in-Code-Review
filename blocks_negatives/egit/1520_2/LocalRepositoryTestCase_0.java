	/**
	 * Activates the item by "pressing" ALT + the character after '&'
	 * 
	 * @param shell
	 * @param itemWithShortcut
	 */
	protected void activateItemByKeyboard(SWTBotShell shell,
			String itemWithShortcut) {
		int index = itemWithShortcut.indexOf('&');
		if (index >= 0 && index < itemWithShortcut.length())
			pressAltAndChar(shell, itemWithShortcut.charAt(index + 1));
	}
